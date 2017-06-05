package me.maweiyi.sso.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import me.maweiyi.service.ApiService;
import me.maweiyi.service.RedisService;
import me.maweiyi.sso.ItemInfo;
import me.maweiyi.sso.pojo.ItemDesc;
import me.maweiyi.sso.pojo.ItemParamItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by maweiyi on 6/2/17.
 */
@Service
public class ItemService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;

    @Autowired
    private ApiService apiService;

    @Autowired
    private RedisService redisService;

    private static String WEB_TAOTAO_TIMEINFO_REDIS = "WEB_TAOTAO_TIMEINFO_KEY";

    public ItemInfo queryItemInfoByItemId(Long itemId) {

        try {
            String json = this.redisService.get(WEB_TAOTAO_TIMEINFO_REDIS + itemId);

            // 商品不存在，直接返回null
            if (StringUtils.isNotBlank(json)) {
                if (StringUtils.equals("404", json)) {
                    return null;
                }
                ItemInfo itemInfo = MAPPER.readValue(json, ItemInfo.class);
                return itemInfo;
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String url = this.MANAGE_TAOTAO_URL + "rest/item/" + itemId;

        try {
            // 调用后台系统接口，获取商品json格式的数据
            String jsonData = this.apiService.doGet(url);
            // 商品不存在，防止多次请求查询，占用服务器资源
            if (StringUtils.equals("", jsonData)) {
                // 放入到缓存中，value为404
                this.redisService.set(WEB_TAOTAO_TIMEINFO_REDIS + itemId, "404", 60 * 60 * 24);
            }
            if (StringUtils.isNotBlank(jsonData)) {
                // 反序列化为ItemInfo对象
                ItemInfo itemInfo = MAPPER.readValue(jsonData, ItemInfo.class);

                // 3.查询到数据，则放入到缓存中
                try {
                    String json = MAPPER.writeValueAsString(itemInfo);
                    this.redisService.set(WEB_TAOTAO_TIMEINFO_REDIS + itemId, json,
                            60 * 60 * 24 * 3);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return itemInfo;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public ItemDesc queryItemDescByItemId(Long itemId) {
        String url = this.MANAGE_TAOTAO_URL + "rest/item/desc/" + itemId;

        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotBlank(jsonData)) {
               ItemDesc itemDesc = MAPPER.readValue(jsonData, ItemDesc.class);
               return itemDesc;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String queryItemParamItemByItemId(Long itemId) {
        String url = this.MANAGE_TAOTAO_URL + "rest/item/param/item/" + itemId;

        // 发起请求，获取商品规格参数数据
        try {
            String jsonData = this.apiService.doGet(url);

            // 反序列化对象，获取paramData
            if (StringUtils.isNotBlank(jsonData)) {
                ItemParamItem itemParamItem = MAPPER.readValue(jsonData, ItemParamItem.class);
                String paramData = itemParamItem.getParamData();

                // 解析paramData，获取ArrayNode
                // JsonNode jsonNode = MAPPER.readTree(paramData);
                ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(paramData);

                // 拼接html代码
                StringBuilder sb = new StringBuilder();
                sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">");
                for (JsonNode jsonNode : arrayNode) {
                    // 拼接组名称
                    sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">"
                            + jsonNode.get("group").asText() + "</th></tr>");
                    // 拼接组内的元素
                    ArrayNode array = (ArrayNode) jsonNode.get("params");
                    for (JsonNode node : array) {
                        sb.append("<tr>");
                        sb.append("<td class=\"tdTitle\">" + node.get("k").asText() + "</td>");
                        sb.append("<td>" + node.get("v").asText() + "</td>");
                        sb.append("</tr>");
                    }
                }
                sb.append("</table>");

                // 返回拼接好的html代码
                return sb.toString();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deleteItemCacheByItemId(Long itemId) {
            this.redisService.delete(WEB_TAOTAO_TIMEINFO_REDIS + itemId);

    }

}

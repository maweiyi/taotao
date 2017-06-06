package me.maweiyi.service;

import me.maweiyi.bean.ItemCatData;
import me.maweiyi.bean.ItemCatResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.maweiyi.pojo.ItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maweiyi on 5/17/17.
 */

@Service
public class ItemCatService extends BaseService<ItemCat> {

    /*@Autowired
    private ItemCatMapper itemCatMapper;


    @Override
    public Mapper<ItemCat> getMapper() {

        return this.itemCatMapper;
    }*/
    private static String MANAGE_TAOTAO_ITEMCAT = "MANAGE_TAOTAO_ITEMCAT";

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 根据父id查询商品类目
     * @param parentId
     * @return
     */
    public List<ItemCat> queryItemCatByParentId(Long parentId) {
        ItemCat param = new ItemCat();
        param.setParentId(parentId);

        List<ItemCat> list = super.queryListByWhere(param);
        return list;
    }

    public ItemCatResult queryItemCatAll() {
        //从缓存中命中,如果命中则返回
        try {

            String jsonData = this.redisService.get(this.MANAGE_TAOTAO_ITEMCAT);
            if (StringUtils.isNotBlank(jsonData)) {
                return MAPPER.readValue(jsonData, ItemCatResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ItemCatResult itemCatResult = new ItemCatResult();
        // 查询所有的类目数据，待用
        List<ItemCat> list = super.queryAll();

        // 把类目封装到MAP中，key为父id，value子类目的集合
        Map<Long, List<ItemCat>> map = new HashMap<Long, List<ItemCat>>();
        // 遍历所有的类目
        for (ItemCat itemCat : list) {
            if (!map.containsKey(itemCat.getParentId())) {
                // 如果该key，value不存在，则创建
                map.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            // 把类目加入
            map.get(itemCat.getParentId()).add(itemCat);
        }

        // 把类目数据封装到ItemCatResult
        List<ItemCat> itemCatList1 = map.get(0l);
        itemCatResult.setItemCats(new ArrayList<ItemCatData>());
        // 装载一级
        for (ItemCat itemCat1 : itemCatList1) {
            ItemCatData itemCatData1 = new ItemCatData();
            itemCatData1.setUrl("/products/" + itemCat1.getId() + ".html");
            itemCatData1.setName("<a href='/products/1.html'>" + itemCat1.getName() + "</a>");
            if (itemCat1.getParent()) {
                List<ItemCat> itemCatList2 = map.get(itemCat1.getId());
                List<ItemCatData> itemCatDataList2 = new ArrayList<ItemCatData>();

                // 把该一级类目的子类目装载(装载二级)
                for (ItemCat itemCat2 : itemCatList2) {
                    ItemCatData itemCatData2 = new ItemCatData();
                    itemCatData2.setUrl("/products/" + itemCat2.getId() + ".html");
                    itemCatData2.setName(itemCat2.getName());
                    // 把该二级类目的子类目(装载三级)
                    List<ItemCat> itemCatList3 = map.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    if (itemCat2.getParent()) {

                        // 遍历二级类目(装载三级)
                        for (ItemCat itemCat3 : itemCatList3) {
                            itemCatData3.add("/products/" + itemCat3.getId() + ".html|"
                                    + itemCat3.getName());
                        }
                        // 装载三级类目
                        itemCatData2.setList(itemCatData3);
                    }
                    // 装载二级类目
                    itemCatDataList2.add(itemCatData2);
                }
                // 装载一级类目
                itemCatData1.setList(itemCatDataList2);
            }
            // 装载到返回对象中
            itemCatResult.getItemCats().add((itemCatData1));
            // 一级类目只能有14个
            if (itemCatResult.getItemCats().size() >= 14) {
                break;
            }
        }
        // 把数据放到缓存中
        try {
            String jsonData = MAPPER.writeValueAsString(itemCatResult);
            if (StringUtils.isNotBlank(jsonData)) {
                this.redisService.set(MANAGE_TAOTAO_ITEMCAT, jsonData, 60 * 60 * 24 * 30);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return itemCatResult;

    }
}

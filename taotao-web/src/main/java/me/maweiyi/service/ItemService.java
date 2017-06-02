package me.maweiyi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.maweiyi.bean.ItemInfo;
import me.maweiyi.pojo.Item;
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

    public Item queryItemInfoByItemId(Long itemId) {
        String url = MANAGE_TAOTAO_URL + "rest/item/" + itemId;

        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotBlank(jsonData)) {
               ItemInfo itemInfo = MAPPER.readValue(jsonData, ItemInfo.class);

               return itemInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}

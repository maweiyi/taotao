package me.maweiyi.service;


import me.maweiyi.bean.EasyUIResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.maweiyi.pojo.Content;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maweiyi on 6/1/17.
 */

@Service
public class IndexService {

    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();




    public Object getAD1() {

        String url = this.MANAGE_TAOTAO_URL + "rest/content?categoryId=2";
        //HttpGet httpGet = new HttpGet(url);
        //CloseableHttpResponse response = null;

        //httpGet.setConfig(config);

        try {
            //response = this.httpClient.execute(httpGet);
            //if (response.getStatusLine().getStatusCode() == 200) {
               String jsonData = this.apiService.doGet(url);
               //String jsonData = EntityUtils.toString(response.getEntity(), "UTF-8");
               if (StringUtils.isNotBlank(jsonData)) {
                  EasyUIResult easyUIResult = EasyUIResult.formatJSONToEasyUIResult(jsonData, Content.class);
                   List<Content> list = (List<Content>) easyUIResult.getRows();

                   List<Map<String, String>> result = new ArrayList<>();
                   for (Content content : list) {
                       Map<String, String> map = new HashMap<>();
                       map.put("srcB", content.getPic());
                       map.put("height", "240");
                       map.put("alt", "");
                       map.put("width", "670");
                       map.put("src", content.getPic());
                       map.put("widthB", "550");
                       map.put("href", content.getPic());
                       map.put("heightB", "240");
                       result.add(map);
                   }

                   return MAPPER.writeValueAsString(result);
               }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

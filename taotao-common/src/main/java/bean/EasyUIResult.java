package bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by maweiyi on 5/18/17.
 */
public class EasyUIResult {
    private Long total;
    private List<?> rows;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    public EasyUIResult() {
    }

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    //把JSON数据反序列化为EasyUIResult
    public static EasyUIResult formatJSONToEasyUIResult(String jsonData, Class<?> clazz) {

        try {

            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode rows = jsonNode.get("rows");
            List<?> list = null;
            if (rows.isArray() && rows.size() > 0) {
                list = MAPPER.readValue(rows.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }

            return  new EasyUIResult(jsonNode.get("total").asLong(), list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EasyUIResult();
    }
}

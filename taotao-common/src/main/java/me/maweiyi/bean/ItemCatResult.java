package me.maweiyi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by maweiyi on 5/24/17.
 */
public class ItemCatResult {

    //JsonProperty注解的作用：对象序列化w为JSON，key为data，实现显示数据后台逻辑的分离
    @JsonProperty("data")
    private List<ItemCatData> itemCats;

    public List<ItemCatData> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<ItemCatData> itemCats) {
        this.itemCats = itemCats;
    }
}

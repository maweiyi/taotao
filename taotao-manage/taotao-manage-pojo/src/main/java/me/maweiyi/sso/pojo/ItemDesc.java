package me.maweiyi.sso.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by maweiyi on 5/18/17.
 */

@Table(name = "tb_item_desc")
public class ItemDesc extends BasePojo {

    @Id
    private Long itemId;

    private String itemDesc;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Override
    public String toString() {
        return "ItemDesc{" +
                "itemId=" + itemId +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }
}

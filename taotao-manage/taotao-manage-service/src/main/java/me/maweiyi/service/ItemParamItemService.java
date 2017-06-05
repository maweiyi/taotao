package me.maweiyi.service;

import me.maweiyi.pojo.ItemParamItem;
import org.springframework.stereotype.Service;

/**
 * Created by maweiyi on 5/19/17.
 */

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

    public ItemParamItem queryItemParamItemByItemId(Long itemId) {
        ItemParamItem param = new ItemParamItem();
        param.setItemId(itemId);
        ItemParamItem itemParamItem = super.queryOne(param);
        return itemParamItem;
    }
}

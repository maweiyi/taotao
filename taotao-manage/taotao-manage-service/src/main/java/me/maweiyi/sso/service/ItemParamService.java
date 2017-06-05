package me.maweiyi.sso.service;

import me.maweiyi.sso.pojo.ItemParam;
import org.springframework.stereotype.Service;

/**
 * Created by maweiyi on 5/19/17.
 */

@Service
public class ItemParamService extends BaseService<ItemParam> {

    public ItemParam queryItemParamByItemCatId(Long itemCatId) {
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(itemCatId);
        ItemParam result = super.queryOne(itemParam);
        return result;
    }

    public void saveItemParam(Long itemCatId, String paramData) {
        ItemParam itemParam = new ItemParam();
        itemParam.setId(null);
        itemParam.setItemCatId(itemCatId);
        itemParam.setParamData(paramData);
        this.save(itemParam);
    }
}

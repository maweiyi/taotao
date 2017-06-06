package me.maweiyi.service;

import com.github.pagehelper.PageInfo;
import me.maweiyi.pojo.Item;
import me.maweiyi.mapper.ItemMapper;
import me.maweiyi.pojo.ItemDesc;
import me.maweiyi.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by maweiyi on 5/18/17.
 */

@Service
public class ItemService extends BaseService<Item> {

    @Value("${MANAGE_TAOTAO_URL}")
    private String WEB_TAOTAO_URL;

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @Autowired
    private ItemMapper mapper;

    @Autowired
    private ApiService apiService;

    public void saveItem(Item item, String desc, String paramData) {
        item.setId(null);
        item.setStatus(1);
        super.save(item);

        //保存商品描述
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);

        this.itemDescService.save(itemDesc);

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setId(null);
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(paramData);
        this.itemParamItemService.save(itemParamItem);
    }

    public PageInfo<Item> queryItemListByPage(Integer page, Integer rows) {
        /*PageHelper.startPage(page, rows);
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        example.createCriteria().andEqualTo("status", 1);
        List<Item> list = this.mapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<Item>(list);*/

        Item item = new Item();
        item.setStatus(1);

        PageInfo<Item> pageInfo = null;
        try {
            pageInfo = super.queryByPage(item, page, rows, "updated Desc");
        } catch (IllegalAccessException e) {

        }
        return pageInfo;
    }

    public void updateItem(Item item, String desc) throws IOException {
        super.updateByIdSelective(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateByIdSelective(itemDesc);

        String url = this.WEB_TAOTAO_URL + "item/cache/" + item.getId() + ".html";
        this.apiService.doGet(url);





    }
}

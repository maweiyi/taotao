package me.maweiyi.service;

import com.github.abel533.mapper.Mapper;
import me.maweiyi.mapper.ItemCatMapper;
import me.maweiyi.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

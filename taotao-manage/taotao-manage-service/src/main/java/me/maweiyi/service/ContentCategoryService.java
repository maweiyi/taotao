package me.maweiyi.service;

import me.maweiyi.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maweiyi on 5/30/17.
 */

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    public List<ContentCategory> queryContentCategoryByParentId(Long parentId) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        List<ContentCategory> list = super.queryListByWhere(contentCategory);

        return list;
    }

    public ContentCategory saveContentCategory(Long parentId, String name) {

        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setId(null);
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setParent(false);

        super.save(contentCategory);

        ContentCategory parent = super.queryById(parentId);
        if (!parent.getParent()) {
            parent.setParent(true);
            super.updateById(parent);
        }

        return contentCategory;
    }

    public void deleteContentCategory(Long parentId, Long id) {
        List<Object> ids = new ArrayList<>();
        ids.add(id);
        this.getIds(ids, id);
        super.deleteByIds(ids);

        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        List<ContentCategory> list = super.queryListByWhere(contentCategory);
        if (list.size() == 0) {
            ContentCategory parent = new ContentCategory();
            parent.setId(parentId);
            parent.setParent(false);
            super.updateByIdSelective(parent);


        }

    }

    private void getIds(List<Object> ids, Long id) {
        ContentCategory param = new ContentCategory();
        param.setParentId(id);
        List<ContentCategory> list = super.queryListByWhere(param);
        for (ContentCategory contentCategory : list) {
            ids.add(contentCategory.getId());
            this.getIds(ids, contentCategory.getId());
        }
    }

}

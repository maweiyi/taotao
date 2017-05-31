package me.maweiyi.controller;

import me.maweiyi.pojo.ContentCategory;
import me.maweiyi.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by maweiyi on 5/30/17.
 */

@RequestMapping("content/category")
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

        try {

            List<ContentCategory> list = this.contentCategoryService.queryContentCategoryByParentId(parentId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(@RequestParam("parentId") Long parentId, @RequestParam("name") String name) {

        try {

            ContentCategory contentCategory = this.contentCategoryService.saveContentCategory(parentId, name);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
        try {

            this.contentCategoryService.updateByIdSelective(contentCategory);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCategory(@RequestParam("parentId") Long parentId, @RequestParam("id") Long id) {
        try {

            this.contentCategoryService.deleteContentCategory(parentId, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


    }
}

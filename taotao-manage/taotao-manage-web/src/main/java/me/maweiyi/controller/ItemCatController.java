package me.maweiyi.controller;

import bean.ItemCatResult;
import me.maweiyi.pojo.ItemCat;
import me.maweiyi.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by maweiyi on 5/17/17.
 */

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {

            List<ItemCat> list = this.itemCatService.queryItemCatByParentId(parentId);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCatAll() {
        try {

            ItemCatResult itemCatResult = this.itemCatService.queryItemCatAll();
            return ResponseEntity.ok(itemCatResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

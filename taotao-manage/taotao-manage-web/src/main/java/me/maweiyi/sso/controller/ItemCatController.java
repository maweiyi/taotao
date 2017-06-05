package me.maweiyi.sso.controller;

import me.maweiyi.bean.ItemCatResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.maweiyi.sso.pojo.Item;
import me.maweiyi.sso.pojo.ItemCat;
import me.maweiyi.sso.service.ItemCatService;
import me.maweiyi.sso.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();


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

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<Item> queryItemById(@PathVariable("itemId") Long itemId) {

        try {

            Item item = this.itemService.queryById(itemId);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /*@RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<String> queryItemCatAll(HttpServletRequest request) {

        try {
            ItemCatResult itemCatResult = this.itemCatService.queryItemCatAll();
            String callback = request.getParameter("callback");
            String result = callback + "(" + MAPPER.writeValueAsString(itemCatResult) + ")";


            return ResponseEntity.ok(new String(result.getBytes(), "UTF-8"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


    }*/

}

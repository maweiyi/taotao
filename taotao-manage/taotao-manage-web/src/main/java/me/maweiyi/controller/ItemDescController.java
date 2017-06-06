package me.maweiyi.controller;

import me.maweiyi.pojo.ItemDesc;
import me.maweiyi.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by maweiyi on 5/18/17.
 */


@RequestMapping("item/desc")
@Controller
public class ItemDescController {


    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId) {
       try {

           ItemDesc itemDesc = this.itemDescService.queryById(itemId);
           return ResponseEntity.ok(itemDesc);
       } catch (Exception e) {
           e.printStackTrace();
       }

       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);



    }
}

package me.maweiyi.sso.controller;

import me.maweiyi.sso.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by maweiyi on 6/4/17.
 */

@RequestMapping("item/cache")
@Controller
public class ItemCacheController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteItemCacheByItemId(@PathVariable("itemId")Long itemId) {
        try {

            this.itemService.deleteItemCacheByItemId(itemId);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

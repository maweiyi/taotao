package me.maweiyi.controller;

import me.maweiyi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by maweiyi on 6/2/17.
 */

@RequestMapping("item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView toItemInfo(@PathVariable("itemId") Long itemId) {
        ModelAndView mv = new ModelAndView("item");
        mv.addObject("item", this.itemService.queryItemInfoByItemId(itemId));
        return mv;

    }
}

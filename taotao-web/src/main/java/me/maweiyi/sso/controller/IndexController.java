package me.maweiyi.sso.controller;

import me.maweiyi.sso.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by maweiyi on 5/21/17.
 */
@RequestMapping("index")
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView toIndex() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("AD1", this.indexService.getAD1());
        return mv;
    }


}

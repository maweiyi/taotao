package me.maweiyi.controller;

import bean.ItemCatResult;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView toIndex() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }


}

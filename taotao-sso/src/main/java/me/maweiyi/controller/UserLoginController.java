package me.maweiyi.controller;

import me.maweiyi.pojo.User;
import me.maweiyi.service.UserService;
import me.maweiyi.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maweiyi on 6/6/17.
 */
@RequestMapping("/service/user")
@Controller
public class UserLoginController {

    @Autowired
    private UserService userService;

    private String TT_TICKET = "TT_TICKET";

    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> doRegister(User user) {
        try {

            this.userService.register(user);

            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> doLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                                                       HttpServletRequest request, HttpServletResponse response) {
        try {

            String ticket = this.userService.login(username, password);
            if (StringUtils.isNotBlank(ticket)) {
                CookieUtils.setCookie(request, response, this.TT_TICKET, ticket, true);

                Map<String, Object> map = new HashMap<>();
                map.put("status", 200);
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

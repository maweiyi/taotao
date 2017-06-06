package me.maweiyi.controller;

import me.maweiyi.service.UserService;
import me.maweiyi.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maweiyi on 6/5/17.
 *
 */
@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
                                         @PathVariable("type") Integer type) {
        try {

            if (type > 0 && type < 4) {
                Boolean bool = this.userService.check(param, type);
                return ResponseEntity.ok(bool);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ResponseEntity<String> register(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> allErrors = result.getAllErrors();

            for (ObjectError objectError : allErrors) {
                errors.add(objectError.getDefaultMessage());
            }

            return ResponseEntity.ok(StringUtils.join(errors, "|"));
        }
        try {

            this.userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam("u") String username, @RequestParam("p") String password) {
        try {

            String ticket = this.userService.login(username, password);
            if (StringUtils.isNotBlank(ticket)) {
                return ResponseEntity.ok(ticket);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "{ticket}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserByTicket(@PathVariable("ticket") String ticket) {
        try {
            User user = this.userService.queryUserByTicket(ticket);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

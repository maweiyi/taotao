package me.maweiyi.sso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.maweiyi.service.RedisService;
import me.maweiyi.sso.mapper.UserMapper;
import me.maweiyi.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by maweiyi on 6/5/17.
 */
@Service
public class UserService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static String SSO_TAOTAO_TICKET = "SSO_TAOTAO_REDIS_TICKET";

    @Autowired
    private RedisService redisService;


    @Autowired
    private UserMapper userMapper;

    public Boolean check(String param, Integer type) {
        User user = new User();

        switch (type) {
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            case 3:
                user.setEmail(param);
                break;
            default:
                break;

        }

        return this.userMapper.selectCount(user) == 0;
    }

    public void register(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        this.userMapper.insert(user);
    }

    public String login(String username, String password) {
        User param = new User();
        param.setUsername(username);

        User user = this.userMapper.selectOne(param);
        try {

            if (StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(password))) {
                String ticket = DigestUtils.md5Hex(System.currentTimeMillis() + username);

                String jsonData = MAPPER.writeValueAsString(user);

                this.redisService.set(SSO_TAOTAO_TICKET + ticket, jsonData, 60 * 60);
                return ticket;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User queryUserByTicket(String ticket) {
        String jsonData = this.redisService.get(SSO_TAOTAO_TICKET + ticket);
        this.redisService.expire(SSO_TAOTAO_TICKET + ticket, 60 * 60);
        if (StringUtils.isNotBlank(jsonData)) {
            try {
                User user = MAPPER.readValue(jsonData, User.class);

                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}

package me.maweiyi.controller;

import me.maweiyi.pojo.Order;
import me.maweiyi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maweiyi on 6/6/17.
 */

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {

        try {

            String orderId = this.orderService.createOrder(order);
            Map<String, Object> map = new HashMap<>();

            map.put("stauts", 200);
            map.put("msg", "OK");
            map.put("data", orderId);
            map.put("ok", true);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/query/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Order> queryOrderByOrderId(@PathVariable("orderId") String orderId) {
        try {

            Order order = this.orderService.queryOrderByOrderId(orderId);

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/query/{buyerNick}/{page}/{rows}", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> queryOrderByPage(@PathVariable("buyerNick") String buyerNick, @PathVariable("page") Integer page, @PathVariable("rows") Integer rows) {

        try {

            List<Order> list = this.orderService.queryOrderByPage(buyerNick, page, rows);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "changeOrderStatus", method = RequestMethod.POST)
    public ResponseEntity<Void> changeOrderStatus(@RequestBody Order order) {
        try {

            this.orderService.changeOrderStatus(order);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

package me.maweiyi.service;

import com.github.pagehelper.PageHelper;
import me.maweiyi.mapper.OrderItemMapper;
import me.maweiyi.mapper.OrderMapper;
import me.maweiyi.mapper.OrderShippingMapper;
import me.maweiyi.pojo.Order;
import me.maweiyi.pojo.OrderItem;
import me.maweiyi.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by maweiyi on 6/6/17.
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    public String createOrder(Order order) {
        String orderId = System.currentTimeMillis() + order.getUserId() + "";

        order.setOrderId(orderId);
        order.setStatus(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());

        this.orderMapper.insertSelective(order);

        List<OrderItem> items = order.getItems();
        for (OrderItem orderItem : items) {
            orderItem.setOrderId(orderId);
            this.orderItemMapper.insertSelective(orderItem);
        }

        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(order.getCreateTime());
        orderShipping.setUpdated(orderShipping.getCreated());

        this.orderShippingMapper.insertSelective(orderShipping);
        return orderId;


    }

    public Order queryOrderByOrderId(String orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        OrderItem param = new OrderItem();
        param.setOrderId(orderId);

        List<OrderItem> items = this.orderItemMapper.select(param);

        order.setItems(items);
        OrderShipping orderShipping = this.orderShippingMapper.selectByPrimaryKey(orderId);
        order.setOrderShipping(orderShipping);
        return order;
    }

    public List<Order> queryOrderByPage(String buyerNick, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);

        Order param = new Order();
        param.setBuyerNick(buyerNick);
        List<Order> list = this.orderMapper.select(param);

        for (int i = 0; i < list.size(); i++) {
            String orderId = list.get(i).getOrderId();
            list.set(i, this.queryOrderByOrderId(orderId));

        }

        return list;
    }

    public void changeOrderStatus(Order order) {
        this.orderMapper.updateByPrimaryKey(order);
    }


}

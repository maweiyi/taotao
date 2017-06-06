package me.maweiyi.controller;

import com.github.pagehelper.PageInfo;
import me.maweiyi.bean.EasyUIResult;
import me.maweiyi.pojo.Item;
import me.maweiyi.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maweiyi on 5/18/17.
 */

@RequestMapping("item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc, @RequestParam("itemParams") String paramData) {

        try {

            this.itemService.saveItem(item, desc, paramData);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {

                LOGGER.error("新增商品失败，商品的信息是：" + item.toString(), e);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemListByPage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {

            PageInfo<Item> pageInfo = this.itemService.queryItemListByPage(page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok().body(easyUIResult);
        } catch (Exception e) {

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc")String desc) {

        try {

            this.itemService.updateItem(item, desc);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}

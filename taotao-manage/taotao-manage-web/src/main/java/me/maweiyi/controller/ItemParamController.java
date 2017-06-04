package me.maweiyi.controller;

import com.github.pagehelper.PageInfo;
import me.maweiyi.bean.EasyUIResult;
import me.maweiyi.pojo.ItemParam;
import me.maweiyi.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maweiyi on 5/19/17.
 */

@RequestMapping("item/param")
@Controller
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)

    public ResponseEntity<ItemParam> queryItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {

        try {

            ItemParam itemParam = this.itemParamService.queryItemParamByItemCatId(itemCatId);
            return ResponseEntity.ok(itemParam);
        } catch (Exception e) {

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


    }

    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId")Long itemCatId, @RequestParam("paramData")String paramData) {
        try {

            this.itemParamService.saveItemParam(itemCatId, paramData);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemParamByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            PageInfo<ItemParam> paramPageInfo =  this.itemParamService.queryByPage(null, page, rows, "updated Desc");
            EasyUIResult easyUIResult = new EasyUIResult(paramPageInfo.getTotal(), paramPageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}

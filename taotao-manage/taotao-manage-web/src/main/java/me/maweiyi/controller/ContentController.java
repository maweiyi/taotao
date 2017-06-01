package me.maweiyi.controller;


import com.github.pagehelper.PageInfo;
import bean.EasyUIResult;
import me.maweiyi.pojo.Content;
import me.maweiyi.service.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maweiyi on 5/30/17.
 */

@RequestMapping("content")
@Controller
public class ContentController {

    private ContentService contentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryContentListByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows, Content content) {
        try {

            PageInfo<Content> pageInfo = this.contentService.queryByPage(content, page, rows, "updated DESC");
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}

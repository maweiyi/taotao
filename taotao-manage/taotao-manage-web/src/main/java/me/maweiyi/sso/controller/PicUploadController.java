package me.maweiyi.sso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.maweiyi.bean.PicUploadResult;
import me.maweiyi.sso.service.PropertiesService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by maweiyi on 5/18/17.
 */

@RequestMapping("pic/upload")
@Controller
public class PicUploadController {

    private static final String[] IMAGE_TYPE = {".jpg", ".jpge", ".png", "bmp", ".gif"};
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private PropertiesService propertiesService;


    @RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile upLoadFile) throws IOException {
        Boolean flag = false;

        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(upLoadFile.getOriginalFilename(), type)) {
                flag = true;
                break;
            }
        }

        PicUploadResult picUploadResult = new PicUploadResult();
        if (!flag) {
            picUploadResult.setError(1);
            String result = OBJECT_MAPPER.writeValueAsString(picUploadResult);
            return result;
        }

        String filePath = getFilePath(upLoadFile.getOriginalFilename());
        File file = new File(filePath);
        upLoadFile.transferTo(file);

        flag = false;
        BufferedImage image = ImageIO.read(file);
        if (image != null) {
            picUploadResult.setWidth(String.valueOf(image.getWidth()));
            picUploadResult.setHeight(String.valueOf(image.getHeight()));

            flag = true;

        }

        if (!flag) {
            file.delete();
        }

        picUploadResult.setError(0);

        String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, propertiesService.IMAGE_FILE_PATH), "/", "/");
        picUploadResult.setUrl(propertiesService.IMAGE_BASE_URL + picUrl);
        String result = OBJECT_MAPPER.writeValueAsString(picUploadResult);

        return result;

    }

    private String getFilePath(String originalFileName) {
        String baseFolder = propertiesService.IMAGE_FILE_PATH + File.separator + "image";
        Date nowDate = new Date();
        String fileFolder = baseFolder + File.separator + new DateTime().toString("yyyy") +
                File.separator + new DateTime().toString("MM") + File.separator + new DateTime().toString("dd");

        File file = new File(fileFolder);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        String fileName = new DateTime().toString("yyyyMMddssSSSS") + RandomUtils.nextInt(1000, 9999)+ "." +
                StringUtils.substringAfterLast(originalFileName, ".");

        return fileFolder + File.separator + fileName;

    }
}

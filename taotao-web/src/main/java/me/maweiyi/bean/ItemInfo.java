package me.maweiyi.bean;

import me.maweiyi.pojo.Item;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by maweiyi on 6/2/17.
 */
public class ItemInfo extends Item {

    public String[] getImages() {
        if (StringUtils.isNotBlank(this.getImage())) {
            String[] images = StringUtils.split(this.getImage(), ",");
            return images;
        }

        return null;
    }
}

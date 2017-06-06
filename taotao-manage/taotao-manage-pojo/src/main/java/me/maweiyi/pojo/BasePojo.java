package me.maweiyi.pojo;

import java.util.Date;

/**
 * Created by maweiyi on 5/17/17.
 */
public abstract class BasePojo {
    private Date created;
    private Date updated;


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}

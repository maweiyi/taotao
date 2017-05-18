package me.maweiyi.bean;

import java.util.List;

/**
 * Created by maweiyi on 5/18/17.
 */
public class EasyUIResult {
    private Long total;
    private List<?> rows;


    public EasyUIResult() {
    }

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}

package com.daiba.global;

import java.util.List;

/**
 * .
 * Creator:aaron_yong
 * Date:2017/1/2
 * Time:20:03
 */
public class DataTableResultVO<T>{
    private int draw;//请求的次数
    private int recordsTotal;//返回的记录总数
    private int recordsFiltered; //过滤后的数据总数
    private List<T> data; // 显示到页面的数据

    public DataTableResultVO() {
    }

    public DataTableResultVO(int draw, int recordsTotal, int recordsFiltered, List<T> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataTableResultVO{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                '}';
    }
}

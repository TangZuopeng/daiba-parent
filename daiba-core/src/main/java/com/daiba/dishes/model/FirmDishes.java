package com.daiba.dishes.model;

/**
 * com.daiba.dishes.model
 *
 * @author TinyDolphin
 *         2017/4/13 15:21.
 */
public class FirmDishes {
    /**
     * 主键码
     */
    private int id;

    /**
     * 流水号
     */
    private String firmId;

    /**
     * 菜品码
     */
    private String dishesId;

    /**
     * 数量
     */
    private int count;

    public FirmDishes() {
        super();
    }

    public FirmDishes(String dishesId, int count) {
        this.dishesId = dishesId;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getDishesId() {
        return dishesId;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FirmDishes{" +
                "id=" + id +
                ", firmId='" + firmId + '\'' +
                ", dishesId='" + dishesId + '\'' +
                ", count=" + count +
                '}';
    }
}

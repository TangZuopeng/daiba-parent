package com.daiba.dishes.model;

/**
 * com.daiba.dishes.model
 *
 * @author TinyDolphin
 *         2017/4/7 9:35.
 */
public class Dishes {

    /**
     * 菜品码：学校(4)+校区(2)+地址(2)+店铺(3)+菜品(3)
     */
    private String dishesId;

    /**
     * 商户id
     */
    private String merchantId;

    /**
     * 菜名
     */
    private String name;

    /**
     * 图片链接
     */
    private String img;

    /**
     * 价格
     */
    private int price;

    /**
     * 折扣(比如8折：80)
     */
    private int discount;

    /**
     * 月销量
     */
    private int monthVolume;

    /**
     * 是否热销(1:是 0:否)
     */
    private int isHot;

    /**
     * 库存
     */
    private int inventory;

    public Dishes() {
        super();
    }

    public Dishes(String dishesId, String merchantId, String name, String img, int price, int discount, int monthVolume, int isHot, int inventory) {
        this.dishesId = dishesId;
        this.merchantId = merchantId;
        this.name = name;
        this.img = img;
        this.price = price;
        this.discount = discount;
        this.monthVolume = monthVolume;
        this.isHot = isHot;
        this.inventory = inventory;
    }

    public String getDishesId() {
        return dishesId;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getMonthVolume() {
        return monthVolume;
    }

    public void setMonthVolume(int monthVolume) {
        this.monthVolume = monthVolume;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Dishes{" +
                "dishesId='" + dishesId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", monthVolume=" + monthVolume +
                ", isHot=" + isHot +
                ", inventory=" + inventory +
                '}';
    }
}

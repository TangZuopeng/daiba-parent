package com.daiba.option.model;

import com.daiba.user.model.User;

import java.util.Date;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public class Option {
    private int optionId;
    /**
     * 标题
     */
    private String optionTitle;
    /**
     * 内容
     */
    private String optionContent;

    /**
     * 反馈时间
     */
    private Date respTime;

    /**
     * 意见回馈用户的实体类
     */
    private User user;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Option() {
    }

    public Option(String optionTitle, String optionContent, Date respTime, User user) {
        this.optionTitle = optionTitle;
        this.optionContent = optionContent;
        this.respTime = respTime;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", optionTitle='" + optionTitle + '\'' +
                ", optionContent='" + optionContent + '\'' +
                ", respTime=" + respTime +
                ", user=" + user +
                '}';
    }
}

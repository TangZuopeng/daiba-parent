package com.daiba.message.service.impl;

import com.daiba.message.model.Article;
import com.daiba.message.model.BaseMessage;
import com.daiba.message.model.NewsMessage;
import com.daiba.message.model.TextMessage;
import com.daiba.message.service.MessageService;
import com.daiba.utils.ConfigUtil;
import com.daiba.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tangzuopeng on 2017/3/16.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Override
    public String getMessage(Map<Object, Object> map) {

        String fromUserName = map.get("FromUserName").toString();
        String toUserName = map.get("ToUserName").toString();
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
            if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE) || map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)){
                //得到活动信息
                Article article1 = getArticle(MessageUtil.ACTIVITY_TITLE, "", MessageUtil.ACTIVITY_JPG_URL, MessageUtil.ACTIVITY_URL);
                Article article2 = getArticle(MessageUtil.APPLY_TIELE, "", MessageUtil.APPLY_JPG_URL, MessageUtil.APPLY_URL);
                Article article3 = getArticle(MessageUtil.LOTTO_TITLE, "", MessageUtil.LOTTO_JPG_URL, MessageUtil.LOTTO_URl);
                NewsMessage newsMessage = getNewsMsg(fromUserName, toUserName, article1, article2, article3);
                return MessageUtil.newsMessageToXml(newsMessage);
            } else if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)){

                if (map.get("EventKey").equals("guide")){
                    //得到操作指南消息
                    Article article = getArticle(MessageUtil.GUIDE_TITLE, MessageUtil.GUIDE_DESCRIPTION, MessageUtil.GUIDE_JPG_URL, MessageUtil.GUIDE_URL);
                    NewsMessage newsMessage = getNewsMsg(fromUserName, toUserName, article);
                    return MessageUtil.newsMessageToXml(newsMessage);
                } else if (map.get("EventKey").equals("classes") || map.get("EventKey").equals("grade")){
                    //得到点击查询课表回复消息
                    TextMessage textMessage = getTextMsg(fromUserName, toUserName,MessageUtil.CLASSES_REPLY);
                    return MessageUtil.textMessageToXml(textMessage);
                } else {
//                    TextMessage textMessage = getTextMsg(fromUserName, toUserName,MessageUtil.REPLY);
                    return "success";
                }
            } else {
//                TextMessage textMessage = getTextMsg(fromUserName, toUserName,MessageUtil.REPLY);
                return "success";
            }
        } else if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
            if (map.get("Content").toString().contains("申请") || map.get("Content").toString().contains("认证") || map.get("Content").toString().contains("接单")){
                //得到关键词回复
                TextMessage textMessage = getTextMsg(fromUserName, toUserName, MessageUtil.WEN_URL);
                return MessageUtil.textMessageToXml(textMessage);
            } else {
//                TextMessage textMessage = getTextMsg(fromUserName, toUserName,MessageUtil.REPLY);
                return "success";
            }
        } else {
//            TextMessage textMessage = getTextMsg(fromUserName, toUserName,MessageUtil.REPLY);
            return "success";
        }
    }

    private TextMessage getTextMsg(String toUserName, String fromUserName, String content){
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(toUserName);
        textMessage.setFromUserName(fromUserName);
        textMessage.setContent(content);
        textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        return  textMessage;
    }

    private NewsMessage getNewsMsg(String toUserName, String fromUserName, Article... articles){
        NewsMessage newsMessage = new NewsMessage();
        List<Article> list=new ArrayList<Article>();
        newsMessage.setToUserName(toUserName);
        newsMessage.setFromUserName(fromUserName);
        newsMessage.setArticleCount(articles.length);
        for (Article i : articles){
            list.add(i);
        }
        newsMessage.setArticles(list);
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        return newsMessage;
    }

    private Article getArticle(String title, String description, String picUrl, String url){
        Article article = new Article();
        article.setDescription(description); //图文消息的描述
        article.setPicUrl(picUrl);
        article.setTitle(title);  //图文消息标题
        article.setUrl(url);  //图文url链接
        return article;
    }
}

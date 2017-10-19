package com.daiba.utils;

/**
 * Created by tangzuopeng on 2017/3/15.
 */
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.daiba.message.model.Article;
import com.daiba.message.model.NewsMessage;
import com.daiba.message.model.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * ClassName: MessageUtil
 *
 * @Description: 消息工具类
 * @author dapengniao
 * @date 2016年3月7日 上午10:05:04
 */
public class MessageUtil {

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 返回消息类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_Image = "image";

    /**
     * 返回消息类型：语音
     */
    public static final String RESP_MESSAGE_TYPE_Voice = "voice";

    /**
     * 返回消息类型：视频
     */
    public static final String RESP_MESSAGE_TYPE_Video = "video";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";


    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：VIEW(自定义菜单URl视图)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * 事件类型：SCAN(扫带参数二维码)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";

    //填写认证信息地址
    public static final String WEN_URL = "https://wj.qq.com/s/1207133/65c5/";

    //查询四六级地址
    public static final String SET_URL = "http://cet.superdaxue.com";

    //查询校园卡余额地址
    public static final String SCHOOL_CARD_URL = "http://msg.weixiao.qq.com/t/39e576cc9a37cdcd552edcc13dfb8913";

    //单身展览馆地址
    public static final String SINGLE_URL = "http://www.pocketuniversity.cn/partner.php?glz=gh_eb9e159be287";

    //工大表白墙地址
    public static final String BIAOBAI_URL = "http://ccut.superdaxue.com/expresswall";

    //工大小秘密地址
    public static final String SECRET_URL = "http://ccut.superdaxue.com/secret";

    //失物招领地址
    public static final String LOST_URL = "http://ccut.superdaxue.com/lostandfound";

    //操作指南封面图片地址
    public static final String GUIDE_JPG_URL = "https://mmbiz.qlogo.cn/mmbiz_jpg/NEO5mZASwjfFYcHuicEdpBUWJ9KHKAPJwQtQNPXVFPtIeyzhdEnoaUc3enVPictG3HVFuxNicQVV45kfR2ydJcQag/0?wx_fmt=jpeg";

    //操作指南图文标题
    public static final String GUIDE_TITLE = "如何使用宅男/女神器";

    //操作指南图文描述
    public static final String GUIDE_DESCRIPTION = "快递来了，不想去取怎么办？天气真冷，不想跑太远吃饭怎么办？好累，只想死在床上怎么办？";

    //操作指南URL
    public static final String GUIDE_URL = "https://mp.weixin.qq.com/s?__biz=MzI3MDI5MTA3NQ==&mid=100000421&idx=1&sn=2444c571a5e10361626135937247539f&chksm=6ad21b495da5925f7ad3ede54a482bb555da0704569a6c00c0dff66390273d7c991ffe09f680&scene=18&key=b47173c065d9d2d954f73408a6866cf0f2d7d2b7195c5dd8a965ab3292682e9481cb91887f193050bafccead34335274b9b16454f4343ed6c3b740528ebd409f9c94c4f92269fae09e68353f2b6bfef5&ascene=7&uin=MjEwNTYzMzMwNg%3D%3D&devicetype=android-23&version=26050434&nettype=WIFI&abtest_cookie=AAACAA%3D%3D&pass_ticket=dcjIwcKmwpOmSUHeHaWeO6Vo%2Bh9zMetspLy2HjWZ1W%2FifmKhTJ8dF7PZWSGatFjp&wx_header=1";

    //扫二维码后or被关注or活动入口图文封面地址
    public static final String ACTIVITY_JPG_URL = "https://mmbiz.qlogo.cn/mmbiz_jpg/NEO5mZASwjcmwicORt3mDfF95wO3ryF6yhqBKGkd243rtKQd5VVrrtF5uGvcGfviaiaSfAT6bfWibl2QPzUcWSLPmg/0?wx_fmt=jpeg";

    //扫二维码后or被关注or活动入口图文标题
    public static final String ACTIVITY_TITLE = "“春荧夏夜，炫彩工大” 2017工大 荧光夜跑";

    //扫二维码后or被关注or活动入口URL
    public static final String ACTIVITY_URL = "https://mp.weixin.qq.com/s/6LKWBrVw6pIL9dimo-ncEw";

    //活动报名图文封面地址
    public static final String APPLY_JPG_URL = "https://mmbiz.qlogo.cn/mmbiz_png/NEO5mZASwjcmwicORt3mDfF95wO3ryF6yp8nlx1zkm7ZYkle28ibrIyibzdBAvEXuiaAqayBrSsn2ssg2XchydV3RQ/0?wx_fmt=gif";

    //活动抽奖封面地址
    public static final String LOTTO_JPG_URL = "https://mmbiz.qlogo.cn/mmbiz_jpg/NEO5mZASwjcmwicORt3mDfF95wO3ryF6yBRkyvP5vVVKaTDhibLwXRuhiagWiaiaOV2wOofgO9iar01OmV5OibHmDXGvg/0?wx_fmt=jpeg";

    //活动抽奖图文标题
    public static final String LOTTO_TITLE = "点此抽奖";

    //活动抽奖入口地址
    public static final String LOTTO_URl = "https://mp.weixin.qq.com/s/EXlA6cifS6NZp-axz0evaA";

    //活动入口标题
    public static final String APPLY_TIELE = "点击夜跑报名";

    //活动报名入口地址
    public static final String APPLY_URL = "https://mp.weixin.qq.com/s/mPQlDgb_b39fI5e8HQJV4g";

    //查询课表or查询成绩回复
    public static final String CLASSES_REPLY = "期末开放，敬请期待！";

    //自动回复
    public static final String REPLY = "客服QQ24小时为您服务，<a href=\"http://wpa.qq.com/msgrd?v=3&uin=421829255&site=qq&menu=yes\">点击此处，联系客服</a>";

    /**
     * @Description: 解析微信发来的请求（XML）
     * @param @param request
     * @param @return
     * @param @throws Exception
     * @author dapengniao
     * @date 2016年3月7日 上午10:04:02
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * @Description: 文本消息对象转换成xml
     * @param @param textMessage
     * @param @return
     * @author dapengniao
     * @date 2016年3月8日 下午4:13:22
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * @Description: 图文消息对象转换成xml
     * @param @param newsMessage
     * @param @return
     * @author dapengniao
     * @date 2016年3月8日 下午4:14:09
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 对象到xml的处理
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}

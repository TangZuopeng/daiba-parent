package com.daiba.firm.service.impl;

import com.daiba.dishes.dao.DishesDao;
import com.daiba.firm.dao.FirmDao;
import com.daiba.firm.model.Firm;
import com.daiba.firm.service.FirmService;
import com.daiba.merchant.dao.MerchantDao;
import com.daiba.user.dao.BringerDao;
import com.daiba.user.dao.UserDao;
import com.daiba.user.model.User;
import com.daiba.utils.SendMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
@Service("firmService")
public class FirmServiceImpl implements FirmService {

    @Autowired
    private FirmDao firmDao;

    @Autowired
    private BringerDao bringerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DishesDao dishesDao;

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public List<Firm> showAllFirm(String acceptAddCode, int orderType, int count) {
        return firmDao.selectAllFirmByOrderAcceptAddCode(acceptAddCode, orderType, count);
    }

    @Override
    public List<Firm> showUserAllFirm(int useId, int orderState) {
        return firmDao.selectAllFirmByUserId(useId, orderState);
    }

    @Override
    public List<Firm> showBringerAllFirm(int briId, int orderState) {
        return firmDao.selectAllFirmByBringerId(briId, orderState);
    }

    @Override
    public Firm acceptFirm(String firmId, int briId) {
        int orderState = 01;//已接单
        Date acceptTime = new Date();
        firmDao.updateFirmAcceptTime(firmId, orderState, acceptTime);
        firmDao.updateFirmBringer(firmId, briId);
        return getFirmDetail(firmId);
    }

    @Override
    public Firm getFirmDetail(String firmId) {
        return firmDao.selectFirmDetail(firmId);
    }

    @Override
    public int cancelFirm(String firmId, String phoneNum) {
        int orderState = 03;//已取消
        Date cancelTime = new Date();
        Firm firm = firmDao.selectFirmDetail(firmId);
        if (firm != null) {
            int firmStatus = firmDao.selectFirmSatus(firmId);
            if (firmStatus == 0) {
                String firmUserPhoneNum = firm.getUser().getPhoneNum();
                if (phoneNum.equals(firmUserPhoneNum)) {
                    //更新订单状态和时间
                    firmDao.updateFirmCancleTime(firmId, orderState, cancelTime);
                    int orderMoney = firm.getOrderMoney();
                    User user = firm.getUser();
                    int userId = user.getUserId();
                    String openId = user.getOpenId();

                    //取消订单之后用户的 发单数-1 金额也减少
                    userDao.downUserOrderNumAndSpending(userId, orderMoney);
                    //封装取消订单模板消息
                    org.json.JSONObject jsonObject = SendMessage.statusJsonmsg("您(" + user.getName() + ")发的单已经取消！", firmId, "已取消", "小带会加倍努力，欢迎您再次下单！");
                    //发送取消订单消息
                    SendMessage.send(openId, SendMessage.STATUS_TEMPLAT, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxefafe5d086334c61&redirect_uri=http://www.ainitzp.cn/daiba-webapp/WeiXin/authorize/&response_type=code&scope=snsapi_userinfo&state=210#wechat_redirect&connect_redirect=1", jsonObject);

                    return 1;
                } else {
                    return 4;
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    //用户确认收货
    /*@Override
    public int confirmFirm(String firmId, String phoneNum, String openId) {
        int orderState = 02;//已完成
        Date finishTime = new Date();
        Firm firm = firmDao.selectFirmDetail(firmId);
        if (firm != null) {
            if (firmDao.selectFirmSatus(firmId) == 1) {
                String firmUserPhoneNum = firm.getUser().getPhoneNum();
                //用户信息与该订单信息匹配情况
                if (phoneNum.equals(firmUserPhoneNum)) {
                    confirmFirmDo(firm, orderState, finishTime, openId);
                    return 1;
                } else {
                    return 4;
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }*/

    /**
     * 带客确认送达
     * @param firmMerchantMessages
     * @param firmId
     * @param briId
     * @param bringerOpenId
     * @return
     */
    @Override
    public int confirmFirm(List<Map<String, Object>> firmMerchantMessages, String firmId, int briId, String bringerOpenId) {
        int orderState = 02;//已完成
        Date finishTime = new Date();
        Firm firm = firmDao.selectFirmDetail(firmId);
        if (firm != null) {
            if (firmDao.selectFirmSatus(firmId) == 1) {
                int firmBriId = firm.getBriId();
                //用户信息与该订单信息匹配情况
                if (briId == firmBriId) {
                    confirmFirmDo(firmMerchantMessages, firm, orderState, finishTime, bringerOpenId);
                    return 1;
                } else {
                    return 4;
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }


    /**
     * 确认到达操作
     *
     * @param firmMerchantMessages
     * @param firm
     * @param orderState
     * @param finishTime
     * @param openId               带客确认收货为带客openId
     */
    private void confirmFirmDo(List<Map<String, Object>> firmMerchantMessages, Firm firm, int orderState, Date finishTime, String openId) {
        //更新订单状态和时间
        firmDao.updateFirmFinishTime(firm.getFirmId(), orderState, finishTime);

        int orderMoney = firm.getOrderMoney();
        //如果当订单类类型不是33(带饭)时，商品金额结算给带客。
//        if (firm.getOrder().getStaId() != 33) {
        orderMoney = orderMoney + firm.getGoodsMoney();
//        }
        System.out.println("商品金额：" + firm.getGoodsMoney());
        System.out.println("金额：" + orderMoney);
        int bringerId = firm.getBriId();

        //带客接单成功之后 接单数+1 金额也增加
        bringerDao.updateAcceptCountAndWeekMoney(bringerId, orderMoney);
        //发单人的openId 方便于发送确认收货的模板消息
        String userOpenId = firm.getUser().getOpenId();
        JSONObject jsonObjectToBringer = SendMessage.statusJsonmsg("您的带吧任务已完成！", firm.getFirmId(), "已完成", "雇佣金将在周五下发到您的钱包！");
        JSONObject jsonObjectToUser = SendMessage.statusJsonmsg("您的带吧任务已完成！", firm.getFirmId(), "已完成", "如有疑问请联系客服！");
        SendMessage.send(openId, SendMessage.STATUS_TEMPLAT, SendMessage.ACCEPT_ORDER_URI, jsonObjectToBringer);
        SendMessage.send(userOpenId, SendMessage.STATUS_TEMPLAT, SendMessage.GIVE_ORDER_URI, jsonObjectToUser);

        if (firm.getOrder().getStaId() == 33) {
            //发完成通知消息给商家,然后记账
            sendMessageToMerchant(firmMerchantMessages, firm);
        } else if (firm.getOrder().getStaId() == 31) {
            //不发通知，记账
            updateMerchantMessage(firmMerchantMessages, firm);
        }
    }

    private void updateMerchantMessage(List<Map<String, Object>> firmMerchantMessages, Firm firm) {
        String firmId = firm.getFirmId();
        String userPhone = firm.getUser().getPhoneNum();
        //StringBuilder dishesNames = new StringBuilder();
        int dishesPrices = 0;
        int saleCount = 0;
        //dishesNames.append(firmMerchantMessages.get(0).get("name") + "×" + firmMerchantMessages.get(0).get("count") + "  ");
        dishesPrices = dishesPrices + (int) firmMerchantMessages.get(0).get("price") * (int) firmMerchantMessages.get(0).get("discount") / 100 * (int) firmMerchantMessages.get(0).get("count");
        saleCount = saleCount + (int) firmMerchantMessages.get(0).get("count");
        dishesDao.updateDishesVolumeById(String.valueOf(firmMerchantMessages.get(0).get("dishes_id")), (int) firmMerchantMessages.get(0).get("count"));
        for (int index = 1; index < firmMerchantMessages.size(); index++) {
            Map<String, Object> map = firmMerchantMessages.get(index - 1);
            Map<String, Object> map2 = firmMerchantMessages.get(index);
            dishesDao.updateDishesVolumeById(String.valueOf(map2.get("dishes_id")), (int) map2.get("count"));
            String merchantId = (String) map.get("merchant_id");
            String merchantId2 = (String) map2.get("merchant_id");
            if (merchantId.equals(merchantId2)) {
                //dishesNames.append(map2.get("name") + "×" + map2.get("count") + "  ");
                dishesPrices = dishesPrices + (int) map2.get("price") * (int) map2.get("discount") / 100 * (int) map2.get("count");
                saleCount = saleCount + (int) map2.get("count");
            } else {
                merchantDao.updateMerchantIncomeAndSaleById(String.valueOf(map.get("merchant_id")), dishesPrices, saleCount);
                //dishesNames.setLength(0);
                dishesPrices = 0;
                saleCount = 0;
                //dishesNames.append(map2.get("name") + "×" + map2.get("count") + "  ");
                dishesPrices = dishesPrices + (int) map2.get("price") * (int) map2.get("discount") / 100 * (int) map2.get("count");
                saleCount = saleCount + (int) map2.get("count");
            }
            if (index == firmMerchantMessages.size() - 1) {
                merchantDao.updateMerchantIncomeAndSaleById(String.valueOf(map2.get("merchant_id")), dishesPrices, saleCount);
            }
        }
    }

    private void sendMessageToMerchant(List<Map<String, Object>> firmMerchantMessages, Firm firm) {
        String firmId = firm.getFirmId();
        String userPhone = firm.getUser().getPhoneNum();
        StringBuilder dishesNames = new StringBuilder();
        int dishesPrices = 0;
        int saleCount = 0;
        dishesNames.append(firmMerchantMessages.get(0).get("name") + "×" + firmMerchantMessages.get(0).get("count") + "  ");
        dishesPrices = dishesPrices + (int) firmMerchantMessages.get(0).get("price") * (int) firmMerchantMessages.get(0).get("discount") / 100 * (int) firmMerchantMessages.get(0).get("count");
        saleCount = saleCount + (int) firmMerchantMessages.get(0).get("count");
        dishesDao.updateDishesVolumeById(String.valueOf(firmMerchantMessages.get(0).get("dishes_id")), (int) firmMerchantMessages.get(0).get("count"));
        for (int index = 1; index < firmMerchantMessages.size(); index++) {
            Map<String, Object> map = firmMerchantMessages.get(index - 1);
            Map<String, Object> map2 = firmMerchantMessages.get(index);
            dishesDao.updateDishesVolumeById(String.valueOf(map2.get("dishes_id")), (int) map2.get("count"));
            String merchantId = (String) map.get("merchant_id");
            String merchantId2 = (String) map2.get("merchant_id");
            if (merchantId.equals(merchantId2)) {
                dishesNames.append(map2.get("name") + "×" + map2.get("count") + "  ");
                dishesPrices = dishesPrices + (int) map2.get("price") * (int) map2.get("discount") / 100 * (int) map2.get("count");
                saleCount = saleCount + (int) map2.get("count");
            } else {
                merchantDao.updateMerchantIncomeAndSaleById(String.valueOf(map.get("merchant_id")), dishesPrices * 65 / 100, saleCount);
                JSONObject jsonObjectToMerchant = SendMessage.statusJsonmsg("您的饭菜已送达给用户！", firmId, "已完成", "菜品：" + dishesNames.toString() + "\n客户手机号：" + userPhone + "\n本周您已售出" + ((int) map.get("week_sale") + saleCount) + "份, " + "共" + ((int) map.get("week_income") + dishesPrices) * 13.0 / 2000 + "元");
                SendMessage.send(String.valueOf(map.get("open_id")), SendMessage.STATUS_TEMPLAT, "", jsonObjectToMerchant);
                dishesNames.setLength(0);
                dishesPrices = 0;
                saleCount = 0;
                dishesNames.append(map2.get("name") + "×" + map2.get("count") + "  ");
                dishesPrices = dishesPrices + (int) map2.get("price") * (int) map2.get("discount") / 100 * (int) map2.get("count");
                saleCount = saleCount + (int) map2.get("count");
            }
            if (index == firmMerchantMessages.size() - 1) {
                merchantDao.updateMerchantIncomeAndSaleById(String.valueOf(map2.get("merchant_id")), dishesPrices * 65 / 100, saleCount);
                JSONObject jsonObjectToMerchant = SendMessage.statusJsonmsg("您的饭菜已送达给用户！", firmId, "已完成", "菜品：" + dishesNames.toString() + "\n客户手机号：" + userPhone + "\n本周您已售出" + ((int) map2.get("week_sale") + saleCount) + "份, " + "共" + ((int) map2.get("week_income") + dishesPrices) * 13.0 / 2000 + "元");
                SendMessage.send(String.valueOf(map2.get("open_id")), SendMessage.STATUS_TEMPLAT, "", jsonObjectToMerchant);
            }
        }
    }

    @Override
    public void payFinishFirm(String firmId) {
        int orderState = 00;
        firmDao.updataFirmPayFinish(firmId, orderState);
    }

    @Override
    public int getFirmStatus(String firmId) {
        return firmDao.selectFirmSatus(firmId);
    }

    @Override
    public int applyCancelFirm(String firmId, User user) {
        Firm firm = firmDao.selectFirmDetail(firmId);
        if (firm != null) {
            if (firmDao.selectIsApplyCancel(firmId) == 1) {
                return 2;
            }
            //订单为已接单状态
            if (firmDao.selectFirmSatus(firmId) == 1) {
                String firmUserPhoneNum = firm.getUser().getPhoneNum();
                //用户信息与该订单信息匹配情况
                if (user.getPhoneNum().equals(firmUserPhoneNum)) {
                    //申请取消
                    firmDao.updateIsApplyCancel(firmId, 1);
                    int bringerId = firm.getBriId();

                    //接单人的openId 方便于发送确认收货的模板消息
                    String openId = userDao.selectOpenIdByBringId(bringerId);
                    JSONObject jsonObject = SendMessage.statusJsonmsg("用户正在申请取消订单，请前往'我接的单'查看！", firm.getFirmId(), "申请取消订单", "您同意以后订单将取消");
                    SendMessage.send(openId, SendMessage.STATUS_TEMPLAT, SendMessage.ACCEPT_ORDER_URI, jsonObject);
                    return 1;
                } else {
                    return 5;
                }
            } else {
                return 3;
            }
        } else {
            return 4;
        }
    }

    @Override
    public int cancelFirmByBringer(String firmId, String phoneNum) {
        int orderState = 03;//已取消
        Date cancelTime = new Date();
        Firm firm = firmDao.selectFirmDetail(firmId);
        if (firm != null) {
            int firmStatus = firmDao.selectFirmSatus(firmId);
            if (firmStatus == 1) {
                User bringer = userDao.selectAcceptUserInfo(firm.getBriId());
                //订单中带客的OpenId
                String bringerOpenId = bringer.getOpenId();
                String firmBringerPhoneNum = bringer.getPhoneNum();
                if (phoneNum.equals(firmBringerPhoneNum)) {
                    //更新订单状态和时间
                    firmDao.updateFirmCancleTime(firmId, orderState, cancelTime);
                    int orderMoney = firm.getOrderMoney();
                    User user = firm.getUser();
                    int userId = user.getUserId();
                    //订单中用户的OpenId
                    String userOpenId = user.getOpenId();

                    //取消订单之后用户的 发单数-1 金额也减少
                    userDao.downUserOrderNumAndSpending(userId, orderMoney);
                    //将申请取消状态还原
                    firmDao.updateIsApplyCancel(firmId, 0);
                    //封装取消订单模板消息
                    JSONObject jsonObjectToUser = SendMessage.statusJsonmsg("您(" + user.getName() + ")发的单已经取消！", firmId, "已取消", "小带会加倍努力，欢迎您再次下单！");
                    JSONObject jsonObjectToBringer = SendMessage.statusJsonmsg("您成功取消了(" + user.getName() + ")发的单！", firmId, "已取消", "小带会加倍努力，欢迎您再次接单！");
                    //发送取消订单消息
                    SendMessage.send(userOpenId, SendMessage.STATUS_TEMPLAT, SendMessage.GIVE_ORDER_URI, jsonObjectToUser);
                    SendMessage.send(bringerOpenId, SendMessage.STATUS_TEMPLAT, SendMessage.ACCEPT_ORDER_URI, jsonObjectToBringer);
                    return 1;
                } else {
                    return 4;
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    //@Override
    //public int confirmFirmInfo(String firmId, String phoneNum) {
    //    Firm firm = firmDao.selectPhoneNumAndMoneyByFirmId(firmId);
    //    if(phoneNum.equals(firm.getUser().getPhoneNum())){
    //        return firm.getOrderMoney();
    //    }
    //    return 0;
    //}

    @Override
    public void addFirm(Firm firm, int userId, String orderId) {
        firmDao.addFirm(firm, userId, orderId);
    }

    @Override
    public List<Firm> loadFirmsToAdmin() throws Exception {
        return firmDao.selectFirmsByTableParams();
    }

    @Override
    public Firm loadFirmDetailToAdmin(String firmId) throws Exception {
        return firmDao.selectFirmDetailToAdmin(firmId);
    }
}

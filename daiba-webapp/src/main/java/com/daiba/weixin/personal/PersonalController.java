package com.daiba.weixin.personal;

import com.daiba.BaseController;
import com.daiba.user.model.ConsigneeMessage;
import com.daiba.user.model.User;
import com.daiba.user.service.PersonalService;
import com.daiba.user.service.UserService;
import com.daiba.utils.GetHostIp;
import com.daiba.utils.SendMessage;
import com.daiba.utils.TokenProccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.daiba.utils.JSONUtils.objectMapper;

/**
 * @author qiuyong
 * @date 2016/10/5.
 * @declare 为快递个人中心提供json数据接口
 */
@Controller
@RequestMapping("/WeiXin/personal")
public class PersonalController extends BaseController {

    @Autowired
    PersonalService personalService;//快递个人信息管理服务层

    @Autowired
    UserService userService;

    //对快递个人_首页操作:显示个人简要信息  退出登录 申请为带客

    /**
     * 获得快递部分个人简要信息
     *
     * @param request
     * @return Str
     */
    @RequestMapping(value = "/ShowUserInfo", method = {RequestMethod.POST}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showPersonalInfo(HttpServletRequest request) {
        int userId = getUserId(request);
        try {
            Map<String, Object> map = personalService.showUserInfo(userId, request);
            return JSONObject.fromObject(map).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 跳转到登录页面
     *
     * @param request
     * @return 重定向Str
     */
    @RequestMapping(value = "/ReturnLogin", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String returnLogin(HttpServletRequest request) {

        request.getSession().setAttribute("user",null);
        request.getSession().setAttribute("address",null);
        request.getSession().setAttribute("ISONLINE",false);

        //此处应该修改，改为 移除 session 中该用户的一些信息。

        //使得整个客户端对应的Session失效，里面的所有东西都会清空了，同时也释放了资源
        //request.getSession().invalidate();

        //request.getSession().removeAttribute("user");
        //request.getSession().removeAttribute("ISONLINE ");

        return "redirect:/WeiXin/main/home";
    }

    /**
     * 发出申请为带客请求
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ApplyBringer", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String applyBringer(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        int userId = user.getUserId();//获取session里面的用户id
        String phoneNum = user.getPhoneNum();
        Map paramMap = new HashMap();
        paramMap.put("useId", userId);
        paramMap.put("userStatus", 20);
        try {
            Map<String, Object> rtnMap = personalService.applyBringer(paramMap, phoneNum);
            return JSONObject.fromObject(rtnMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 跳转到个人信息详情页面
     *
     * @return str 页面跳转地址
     */
    @RequestMapping(value = "EnterMineDetail", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String enterMineDetail(HttpSession session) {
        if (isLogin(session)) {
            return "wx/personal/mine_detail";
        } else {
            return "redirect:/WeiXin/login";
        }
    }

//关于地址管理

    /**
     * 跳转到地址管理页面
     *
     * @param session
     * @return 地址跳转
     */
    @RequestMapping(value = "/EnterManageAdd", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String enterAddressManage(HttpSession session, HttpServletRequest request) {

        if (isLogin(session)) {
            return "wx/personal/address_main";
        } else {
            return "redirect:/WeiXin/login";
        }
    }

    /**
     * 根据用户id号 查询该用户拥有的个人地址列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ShowAddresses.do", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plaim;charset=utf-8")
    @ResponseBody
    public String showAddresses(HttpServletRequest request) {
        int userId = getUserId(request);
        try {
            Map<String, Object> rtnMap = personalService.showDeliveryAddresses(userId);
            String json = JSONObject.fromObject(rtnMap).toString();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 根据地址id号获取指定地址
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/GetAddress.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getAddress(HttpServletRequest request) {
        int conmsgId = Integer.parseInt(request.getParameter("conmsgId").trim().toString());
        try {
            Map<String, Object> rtnMap = personalService.showDeliveryAddress(conmsgId);
            return JSONObject.fromObject(rtnMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 根据地址id号删除指定地址
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/DeleteDeliveryAddress.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String deleteDeliveryAddress(HttpServletRequest request) {
        int conmsgId = Integer.parseInt(request.getParameter("conmsgId").trim().toString());
        try {
            Map<String, Object> rtnMap = personalService.deleteDeliveryAddress(conmsgId);
            return JSONObject.fromObject(rtnMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 根据用户id号添加地址
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/AddDeliveryAddress.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String addDeliveryAddress(HttpServletRequest request, ConsigneeMessage message) {
        int userId = getUserId(request);
        message.setUseId(userId);
        try {
            Map<String, Object> resultMap = personalService.addDeliveryAddress(message);
            return JSONObject.fromObject(resultMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 根据conmsgId 收货地址编码号修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/EditDeliveryAddress.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String editDeliveryAddress(HttpServletRequest request, ConsigneeMessage message) {
        try {
            Map<String, Object> resultMap = personalService.editDeliveryAddress(message);
            return JSONObject.fromObject(resultMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    @RequestMapping(value = "/EnterAddress", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plain;charset=utf-8")
    public String enterAddress(HttpSession session, HttpServletRequest request) {
        if (isLogin(session)) {
            return "wx/personal/per_address";
        } else {
            return "redirect:/WeiXin/login";
        }
    }

//信息详情页面

    /**
     * 显示用户想详情信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ShowDetailUser.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showDetailUser(HttpServletRequest request) {
        int userId = getUserId(request);
        try {
            Map<String, Object> resultMap = personalService.showDetailedUserInfo(userId, request);
            String json = JSONObject.fromObject(resultMap).toString();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 修改用户昵称
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/EditNickName.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String editNickName(HttpServletRequest request) {
        int userId = getUserId(request);
        String name = request.getParameter("nickName").toString().trim();

        //name = name.replaceAll("[^\\u0000-\\uFFFF]]","");
        name = name.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        name = name.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", "");

        if(name.contains("高校跑腿君")){
            name = name.replaceAll("高校跑腿君","XXXXX");
        }

        if(name.contains("希必地")){
            name = name.replaceAll("希必地","XXX");
        }

        Map map = new HashMap();
        map.put("useId", userId);
        map.put("nickName", name);
        try {
            Map<String, Object> resultMap = personalService.editUserNickName(map);
            String resultJson = JSONObject.fromObject(resultMap).toString();
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 修改用户性别
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/EditSex.do", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String editSex(HttpServletRequest request) {
        int userId = getUserId(request);
        int sexCode = Integer.parseInt(request.getParameter("sexCode").toString().trim());
        try {
            Map<String, Object> resultMap = personalService.editSex(userId, sexCode);
            String resultJson = JSONObject.fromObject(resultMap).toString();
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    @RequestMapping(value = "/EditIsReceiver.do", method = {RequestMethod.POST}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String updateIsReceiver(HttpServletRequest request){
        int userId = getUserId(request);
        int isReceiver = Integer.parseInt(request.getParameter("isReceiver").toString().trim());
        try {
            Map<String, Object> resultMap = personalService.editIsReceiver(userId, isReceiver);
            String resultJson = JSONObject.fromObject(resultMap).toString();
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 进入用户本地相册，以便修改用户头像
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/EnterAlbum", method = {RequestMethod.GET}, produces = "text/plain;charset=utf-8")
    public String enterAlbum(HttpSession session) {
        if (isLogin(session)) {
            return "wx/personal/user_album";
        } else {
            return "redirect:/WeiXin/login";
        }
    }

    /**
     * 进入到修改密码的页面
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/changerPassword", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String enterEditPassword(HttpSession session,HttpServletRequest request) {
        //生成防止重复提交的令牌
        String token = TokenProccessor.getInstance().makeToken();//创建令牌
        request.getSession().setAttribute("token", token);   ///在服务器使用session保存token(令牌)
        if (isLogin(session)) {
            return "wx/personal/edit_password";
        } else {
            return "redirect:/WeiXin/login";
        }
    }

    /**
     * 修改用户密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/changerPasswordPost.do", method = RequestMethod.POST)
    @ResponseBody
    public String changerPasswordPost(HttpServletRequest request) {
        Map<String, Object> changeMessages = new HashMap<String, Object>();
        User userInfo = getUserInfo(request);
        String openId = userInfo.getOpenId();
        String phoneNum = userInfo.getPhoneNum();
        int flag = userService.changerPassword(phoneNum,request);
        if(flag==1){

            //修改密码之后，清除登录信息
            request.getSession().setAttribute("user",null);
            request.getSession().setAttribute("address",null);
            request.getSession().setAttribute("ISONLINE",false);

            //发送模板消息，提示用户密码已经被修改
            org.json.JSONObject jsonObject = SendMessage.passwordChangeJsonmsg("检测到您的密码被修改！", SendMessage.format.format(System.currentTimeMillis()), GetHostIp.getIpAddr(request), "如非本人操作，请及时联系管理员处理。");
            SendMessage.send(openId, SendMessage.CHANGE_PASSWORD_TEMPLAT, "", jsonObject);
        }
        changeMessages.put("changeMessage", flag);
        try {
            return objectMapper.writeValueAsString(changeMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

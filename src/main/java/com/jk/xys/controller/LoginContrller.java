package com.jk.xys.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jk.xys.pojo.LoginBean;


import com.jk.xys.service.Login2Service;
import com.jk.xys.utlis.ConstanConf;
import com.jk.xys.utlis.HttpClientUtil;
import com.jk.xys.utlis.Md5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("log")
public class LoginContrller {
    @Autowired
    private Login2Service login2Service;

    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> loginUser(LoginBean loginBean, HttpServletRequest request){
        return login2Service.LoginUserS(loginBean,request);
    }
    //快速登陆
    @RequestMapping("messageLogin")
    @ResponseBody
    public HashMap<String, Object> messageLogin(String messageCode,String name,HttpSession session) {
        return login2Service.messageLogin(messageCode, name, session);
    }
    //获取短信验证码
    @RequestMapping("MessgerCode")
    @ResponseBody
    public HashMap<String, Object>  MessgerCode(String name, HttpSession session){
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        Object object = redisTemplate.opsForValue().get(ConstanConf.SMS_LOHIN_CODE+"sss");
        if (object!=null) {
            result.put("code", 2);
            result.put("msg","请在一分钟后发送");
            return result;
        }
        params.put("accountSid",ConstanConf.ACCOUNTSID);
        params.put("to",name);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        params.put("timestamp", timestamp);
        String sig = Md5Util.getMd532(ConstanConf.ACCOUNTSID+ConstanConf.AUTH_TOKEN+timestamp);
        params.put("sig", sig);
        params.put("templateid",ConstanConf.TEMPLATEID);
        Integer random=(int) (Math.random()*899999+100000);
        System.out.println(random);
        redisTemplate.opsForValue().set(ConstanConf.SMS_LOHIN_CODE+"sss",random,ConstanConf.SMS_CODE_TIME, TimeUnit.MINUTES);
        //session.setAttribute("sss", random);
        params.put("param", random);
        String post = HttpClientUtil.post(ConstanConf.SMS_URL,params);
        JSONObject jsonObject = JSON.parseObject(post);
        String respCode = jsonObject.getString("respCode");
        System.out.println(respCode);
        if (ConstanConf.SMS_SUCCESS.equals(respCode)) {
            redisTemplate.opsForValue().set(ConstanConf.SMS_LOHIN_Lock+"sss", "1",ConstanConf.SMS_CODE_TIME_LOCKE,TimeUnit.MINUTES);
            result.put("code", 0);
            result.put("msg","发送成功");
            return result;
        }else {
            result.put("code", 1);
            result.put("msg","发送失败");
            return result;
        }


    }
}

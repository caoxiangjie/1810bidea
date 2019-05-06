package com.jk.xys.service;


import com.alibaba.dubbo.config.annotation.Reference;


import com.jk.xys.dao.LoginDao;
import com.jk.xys.pojo.LoginBean;
import com.jk.xys.utlis.ConstanConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
public class LoginServiceImpl implements Login2Service {
    @Reference
     LoginService loginService;
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private RedisTemplate redisTemplate;
    public HashMap<String, Object> LoginUserS(LoginBean loginBean, HttpServletRequest request) {
        HashMap<String, Object> stringObjectHashMap = loginService.LoginUser(loginBean);
        HttpSession session = request.getSession();
        session.setAttribute("user",stringObjectHashMap);
        System.out.println(session);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> messageLogin(String messageCode, String name, HttpSession session) {
        HashMap<String, Object> result= new HashMap<String, Object>();
        Object object = redisTemplate.opsForValue().get(ConstanConf.SMS_LOHIN_CODE+"sss");
        //Object attribute = session.getAttribute("sss");
        if (object==null) {
            result.put("code", 1);
            result.put("msg", "验证码错误");
            return result;
        }
        if (!messageCode.equals(object.toString())) {
            result.put("code", 1);
            result.put("msg", "验证码错误");
            return result;
        }
        LoginBean login= loginDao.queryPhoneNumber(name);
        if (login==null) {
            result.put("code", 2);
            result.put("msg", "手机号码异常");
            return result;
        }

        session.setAttribute("user", login);
        result.put("code", 0);
        result.put("msg", "登录成功");

        return result;
    }
}

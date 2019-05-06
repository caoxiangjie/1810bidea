package com.jk.xys.service;

import com.jk.xys.pojo.LoginBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface Login2Service {
    HashMap<String, Object> LoginUserS(LoginBean loginBean, HttpServletRequest request);

    HashMap<String, Object> messageLogin(String messageCode, String name, HttpSession session);
}

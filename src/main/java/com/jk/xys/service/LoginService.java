package com.jk.xys.service;

import com.jk.xys.pojo.LoginBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface LoginService {
    HashMap<String,Object> LoginUser(LoginBean loginBean);

}

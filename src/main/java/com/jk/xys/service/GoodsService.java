package com.jk.xys.service;

import com.jk.xys.pojo.NavBean;

import java.util.HashMap;
import java.util.List;

public interface GoodsService {
    List<NavBean> queryNav();

    HashMap<String, Object> queryGoods(Integer page, Integer rows);

    void updateCommentsCount(Integer goodsId, long count);
}

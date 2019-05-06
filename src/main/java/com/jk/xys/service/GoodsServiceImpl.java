package com.jk.xys.service;


import com.jk.xys.dao.GoodsDao;
import com.jk.xys.pojo.GoodsBean;
import com.jk.xys.pojo.NavBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<NavBean> queryNav() {
        Integer pid=0;
        return queryByPid(pid);
    }

    @Override
    public HashMap<String, Object> queryGoods(Integer page, Integer rows) {
        HashMap<String, Object> hashMap = new HashMap<>();
        int total=goodsDao.queryCount();
        int start=(page-1)*rows;
        List<GoodsBean> queryGoods = goodsDao.queryGoods(start,rows);
        hashMap.put("total", total);
        hashMap.put("rows", queryGoods);
        return hashMap;
    }

    @Override
    public void updateCommentsCount(Integer goodsId, long count) {
        goodsDao.updateCommentsCount(goodsId,count);
    }


    private List<NavBean> queryByPid(Integer pid) {
        List<NavBean> navlist=goodsDao.queryNavByPid(pid);
        for (NavBean navBean : navlist) {
            List<NavBean> queryByPid = queryByPid(navBean.getId());
            if (queryByPid!=null&&queryByPid.size()>0) {
                navBean.setNodes(queryByPid);
                navBean.setSeletetable(false);
            }else{
                navBean.setSeletetable(true);
            }
        }
        return navlist;
    }

}

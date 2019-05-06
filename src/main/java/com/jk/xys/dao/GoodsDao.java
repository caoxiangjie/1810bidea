package com.jk.xys.dao;

import com.jk.xys.pojo.GoodsBean;
import com.jk.xys.pojo.NavBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsDao {
     List<NavBean> queryNavByPid(Integer pid);

     int queryCount();

     List<GoodsBean> queryGoods(@Param("start") int start, @Param("rows") Integer rows);

    void updateCommentsCount(@Param("goodsId")Integer goodsId,@Param("count") long count);
}

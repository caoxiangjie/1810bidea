package com.jk.xys.controller;

import com.jk.xys.pojo.Comments;
import com.jk.xys.pojo.LogBean;
import com.jk.xys.pojo.NavBean;
import com.jk.xys.service.GoodsService;

import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("queryNav")
    @ResponseBody
    public List<NavBean> queryNav() {
        return goodsService.queryNav();
    }

    @RequestMapping("queryGoods")
    @ResponseBody
    public HashMap<String, Object> queryGoods(Integer page, Integer rows) {
        return goodsService.queryGoods(page, rows);
    }

    @RequestMapping("addComments")
    @ResponseBody
    public Boolean addComments(Comments comments) {
        try {

            Date date = new Date();
            comments.setCommentDate(date);
            String commentsStars = comments.getCommentsStars();
            String[] split = commentsStars.split(",");
            comments.setCommentsStars(split.length+"");
            mongoTemplate.insert(comments);
            Query query = new Query();
            query.addCriteria(Criteria.where("goodsId").is(comments.getGoodsId()));
            long count = mongoTemplate.count(query,Comments.class);
            goodsService.updateCommentsCount(comments.getGoodsId(),count);
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;


        }
    }
    @RequestMapping("queryComments")
    @ResponseBody
    public  HashMap<String,Object> queryComments(Integer page,Integer rows,Integer id,Comments comments){
        HashMap<String, Object> hashMap = new HashMap<>();
        Query query = new Query();
        if (id!=null) {
            query.addCriteria(Criteria.where("goodsId").is(id));
        }
        if (comments.getComments()!=null&&!comments.getComments().equals("")) {
            Pattern compile = Pattern.compile("^.*"+comments.getComments()+".*$",Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("comments").regex(comments.getComments()));
        }
        /*Criteria criteria = new Criteria();
        if(comments.getStartTime()!= null ) {
            criteria = Criteria.where("commentDate").lte(comments.getStartTime());
        }else {
            criteria = Criteria.where("commentDate");
        }
        if(comments.getEndTime() != null) {
            criteria.gte(comments.getEndTime());
        }
        if(comments.getStartTime() != null || comments.getEndTime() != null) {
            query.addCriteria(criteria);
        }*/
        long count = mongoTemplate.count(query,Comments.class);
        List<Comments> ob = (List<Comments>) redisTemplate.opsForValue().get(page);
        if (comments==null) {
            if (ob != null && !ob.toString().equals("")) {
                hashMap.put("rows", ob);
                hashMap.put("total", count);
                return hashMap;
            }
        }
        //分页
        query.skip((page-1)*rows);
        query.limit(rows);
        List<Comments> comment = mongoTemplate.find(query, Comments.class);
        if (comments==null) {
            redisTemplate.opsForValue().set(page, comment);
        }
        hashMap.put("total", count);
        hashMap.put("rows", comment);
        return hashMap;
    }
    @RequestMapping("queryLog")
    @ResponseBody
    public  HashMap<String,Object> queryLog(Integer page,Integer rows){
        HashMap<String, Object> hashMap = new HashMap<>();
        Query query = new Query();

        long count = mongoTemplate.count(query, LogBean.class);


        query.skip((page-1)*rows);
        query.limit(rows);
        List<LogBean> comments = mongoTemplate.find(query, LogBean.class);

        hashMap.put("total", count);
        hashMap.put("rows", comments);
        return hashMap;
    }
}
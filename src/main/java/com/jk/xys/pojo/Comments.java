package com.jk.xys.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection ="t_comments")
@Data
public class Comments implements Serializable {

    private Integer goodsId;
    private String comments;
    private String commentsName;
    private Integer commentsLevel;
    private String commentsStars;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date commentDate;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsName() {
        return commentsName;
    }

    public void setCommentsName(String commentsName) {
        this.commentsName = commentsName;
    }

    public Integer getCommentsLevel() {
        return commentsLevel;
    }

    public void setCommentsLevel(Integer commentsLevel) {
        this.commentsLevel = commentsLevel;
    }

    public String getCommentsStars() {
        return commentsStars;
    }

    public void setCommentsStars(String commentsStars) {
        this.commentsStars = commentsStars;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
}

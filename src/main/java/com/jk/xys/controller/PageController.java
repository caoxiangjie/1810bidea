package com.jk.xys.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("page")
public class PageController {
    @RequestMapping("toShow")
    public String toShow(){
        return "show";
    }
    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("toMain")
    public String toMain(){
        return "main";
    }
    @RequestMapping("toComments")
    public String toComments(Integer id, Model model){
        model.addAttribute("id",id);
        return "comments";
    }
    @RequestMapping("commentsShow")
    public String commentsShow(){
        return "commentsShow";
    }
    @RequestMapping("noteLogin")
    public String noteLogin(){
        return "noteLogin";
    }
    @RequestMapping("tolog")
    public String tolog(){
        return "log";
    }
}

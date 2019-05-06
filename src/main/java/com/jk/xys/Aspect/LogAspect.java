package com.jk.xys.Aspect;

import com.jk.xys.pojo.LogBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Pointcut("execution(* com.jk.xys.service.*.*(..))")
    public void logPointCut(){

    }
    @AfterReturning(value="logPointCut()",argNames = "rtv", returning = "rtv")
    public void saveLog(JoinPoint jp, Object rtv){
        LogBean logBean = new LogBean();

        //获取方法名
        String className = jp.getTarget().getClass().getName();
        logBean.setClassName(className);

        //获取类名
        String methodName = jp.getSignature().getName();
        logBean.setMethodName(methodName);

        //获取参数
        Object[] args = jp.getArgs();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < args.length; i++) {

            stringBuffer.append("第"+i+"个参数=").append(args[i].toString());


        }
        logBean.setParams(stringBuffer.toString());
        logBean.setResponseBody(rtv==null?"":rtv.toString());
        logBean.setCreateTime(new Date());
       /* RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        if(sra != null) {
            HttpServletRequest request = sra.getRequest();
            logBean.setIp(IpUtil.getIpAddr(request));
            HttpSession session = request.getSession();
            UserBean userBean = (UserBean) session.getAttribute("user");

            if (userBean==null) {
                logBean.setUserId(222);
            }else{
                logBean.setUserId(userBean.getId());
            }
        }*/
        mongoTemplate.insert(logBean);

    }
}

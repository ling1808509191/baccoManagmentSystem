package com.mark.java.Interceptor;

import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.Account;
import com.mark.java.serviceImp.AccountServiceImp;
import com.mark.java.staticTool.staticToll;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by vcc on 2017/3/25.
 */
public class AccountCheckInterceptor  implements HandlerInterceptor{
    @Autowired
    private AccountServiceImp AccountService;
    private long ReloginTime=12*60*60;
    private long millisecond=1000;

    public AccountServiceImp getAccountService() {
        return AccountService;
    }

    public void setAccountService(AccountServiceImp accountService) {
        AccountService = accountService;
    }

    private void ReturnErrorMessage(HttpServletResponse response,String message){

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(message==null){
                out.append("{ \"message\":\"�����֤ʧ��\"}");
            }else{
                out.append(message);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {


        String sign=null;
        String uid=null;
        String time=null;
        String platform=null;
        long requestTime;
        long currenTime;
        System.out.println("uri : "+httpServletRequest.getServletPath());
        System.out.println(httpServletRequest.getContentType());

        if("/user/logincheck".equals(httpServletRequest.getServletPath())){
            System.out.println("get /user/logincheck");
            System.out.println(httpServletRequest.getContentType());

            return true;
        }
        try{
            sign=httpServletRequest.getParameter("sign");
            uid=httpServletRequest.getParameter("request_uid");
            time=httpServletRequest.getParameter("time");
            platform=httpServletRequest.getParameter("from_platform");
            currenTime=System.currentTimeMillis();
            requestTime=Long.valueOf(time);
        }catch (Exception e){
            ReturnErrorMessage(httpServletResponse,"{ \"message\":\"��֤��Ϣȱʧ\"}");
            return false;
        }



        System.out.println("current time : "+currenTime);


        String URI=httpServletRequest.getServletPath();
        String tokenAndLoginTime=null;
        System.out.println("getRequestURI =: "+URI);
        resultBean resultBean=AccountService.getAccountById(Integer.valueOf(uid));
        Account account=null;
        if(resultBean.getData().size()!=0)
            account=(Account) resultBean.getData().get(0);
        if(account==null){
            ReturnErrorMessage(httpServletResponse,null);
            return false;
        }if(URI.contains("/Admin")&&account.is_admin()==false){

            ReturnErrorMessage(httpServletResponse,null);
            return false;
        }

        if(platform.equals("app")){
            tokenAndLoginTime=account.getApp_token();
        }else if(platform.equals("web")){
            tokenAndLoginTime=account.getWeb_token();
        }
        if(tokenAndLoginTime==null){
            ReturnErrorMessage(httpServletResponse,"{ \"message\":\"���ݿ�tokenΪ��\"}");
            return false;
        }
        String token=null;
        long loginTime=-1;
        if(tokenAndLoginTime!=null&&!tokenAndLoginTime.equals("")&&tokenAndLoginTime.contains(";")){
            token=tokenAndLoginTime.split(";")[0];
            loginTime=Long.valueOf(tokenAndLoginTime.split(";")[1]);
        }else{
            ReturnErrorMessage(httpServletResponse,"{ \"message\":\"���ݿ�tokenΪ����\"}");
        }
        System.out.println("loginTime :��"+loginTime);
        System.out.println("currentTime : "+currenTime);
        if(loginTime==-1||currenTime-loginTime<0||currenTime-loginTime>(ReloginTime*millisecond)){
            ReturnErrorMessage(httpServletResponse,"{ \"message\":\"��½ʱ�䳬�������\"}");
            return false;
        }
        String signString=URI+"?token="+token+"&time="+time;

        String md5String= staticToll.md5Password(signString);
        System.out.println("befor md5 string : "+signString);
        System.out.println("md5 string : "+md5String);
        if(md5String.equals(sign)){
        return true;
        }else{

            ReturnErrorMessage(httpServletResponse,"{ \"message\":\"signֵ������Ҫ�����md5�ַ���Ϊ : "+signString+" ,������ֵΪ : "+md5String+"\"}");
            return false;
        }


    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "v1",description = "用户管理系统接口")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private  SqlSessionTemplate template;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "这个是用户登录接口",httpMethod = "POST")
    public Boolean login(HttpServletResponse response,
                         @RequestBody User user){
        int i = template.selectOne("login",user);
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是：  " +i);
        if(i==1){
        //   log.info("登录的用户是：" + user.getUserName());
           return true;
        }

    return false;
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "这个是添加用户的接口",httpMethod = "POST")
    public Boolean addUser(HttpServletRequest request,@RequestBody User user){
        //验证cooke是否获取成功
        Boolean x = verifyCookies(request);

        int resulet=0;
        if(x != null){
            resulet = template.insert("addUser",user);
        }
        if (resulet > 0){
            log.info("添加用户为： " + resulet);
            return true;
        }
        return false;

    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "这个是获取用户列表的接口",httpMethod = "POST")
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        if(x == true){
           List<User> users = template.selectList("getUserInfo",user);
           log.info("获取的用户信息是" + users.size() + "个");
           return users;
        }else
            return null;
    }
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "这个是更新用户的接口",httpMethod = "POST")
    public int updateUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if(x == true){
           i = template.update("updateUserInfo",user);
           log.info("更新了 " +  i +" 数 据");
        }
        return i;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return false;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login")
            && cookie.getValue().equals("true")){
                log.info("---------------获取cookie成功-----------");
                return true;
            }
        }
        return false;
    }
}

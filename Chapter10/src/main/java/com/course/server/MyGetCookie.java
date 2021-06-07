package coml.course.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyGetCookie {

    @RequestMapping(value = "/getcookies",method = RequestMethod.GET)
    public String getCookies(){

        return "恭喜你获得cookie信息成功";
    }
}

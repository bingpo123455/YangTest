package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AddUserTest {

    @Test(enabled = true,dependsOnGroups = "loginTrue",description = "这个是测试增加用户的接口")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase =session.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发送请求  返回结果
        String result = getResult(addUserCase);
        //验证返回结果
     /*   Thread.sleep(5000);
        User user = session.selectOne("addUser",addUserCase);
        System.out.println(user.toString());
        Assert.assertEquals(addUserCase.getExpected(),result);
*/
        Thread.sleep(3000);
       SqlSession session1 = DatabaseUtil.getSqlSession();
        User users = session1.selectOne("addUser",addUserCase);
       // Thread.sleep(8000);
        System.out.println(users.toString());


        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost httpPost = new HttpPost(TestConfig.addUserUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",addUserCase.getUserName());
        jsonObject.put("password",addUserCase.getPassword());
        jsonObject.put("sex",addUserCase.getSex());
        jsonObject.put("age",addUserCase.getAge());
        jsonObject.put("permission",addUserCase.getPermission());
        jsonObject.put("isDelete",addUserCase.getIsDelete());

        httpPost.setHeader("Content-Type","application/json");
       // T
        StringEntity entity =  new StringEntity(jsonObject.toString(),"utf-8");
        httpPost.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);

        HttpResponse response = TestConfig.defaultHttpClient.execute(httpPost);
       String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
       return result;
    }
}

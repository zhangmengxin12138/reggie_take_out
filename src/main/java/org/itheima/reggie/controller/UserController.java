package org.itheima.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itheima.reggie.Utils.ValidateCodeUtils;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.User;
import org.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j

public class UserController {
    @Autowired
    private UserService userService;


    /*
    *
    * 发送手机验证码*/
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码：{}",code);
            session.setAttribute(phone,code);
            return R.success("验证码发送成功");
        }
        return R.error("手机号错误");
    }

}

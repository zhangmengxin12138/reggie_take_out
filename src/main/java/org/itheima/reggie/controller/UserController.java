package org.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itheima.reggie.Utils.ValidateCodeUtils;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.User;
import org.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /*
     *
     * 发送手机验证码*/
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {

        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码：{}", code);
            session.setAttribute(phone, code);
            return R.success("验证码发送成功");
        }
        return R.error("手机号错误");
    }
/*
* 登录验证*/
    @PostMapping("login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        Object attribute = session.getAttribute(phone);
        if (attribute!=null&& attribute.equals(code)){
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone,phone);
            User one = userService.getOne(wrapper);
            if (one==null){
                User user = new User();
                user.setPhone(phone);
                userService.save(user);
                session.setAttribute("user",user.getId());
                return R.success(user);
            }
            session.setAttribute("user",one.getId());
            return  R.success(one);
        }
        return R.error("验证码错误");
    }

}

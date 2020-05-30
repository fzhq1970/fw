package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.Result;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.service.AdminUserService;
import com.ccb.ha.fw.base.vo.VoUserRegister;
import com.ccb.ha.fw.util.PasswordEncoder;
import com.ccb.ha.fw.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/api/user")
public class AdminUserController extends BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    AdminUserService systemUserService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody VoUserRegister user) {
        if (log.isDebugEnabled()) {
            log.debug("user register : [{}].", user);
        }
        String username = user.getUsername();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        String mail = HtmlUtils.htmlEscape(user.getEmail());
        user.setEmail(mail);
        String phone = HtmlUtils.htmlEscape(user.getPhone());
        user.setPhone(phone);
        //判断是不是用户已经存在
        boolean exist = systemUserService.isExist(username, mail, phone);
        if (exist) {
            String message = "用户名/邮件/手机号码已被使用";
            return Result.error(message);
        }
        //生成新用户数据
        AdminUser auser = this.cvtRegisterUser(user);
        if (log.isDebugEnabled()) {
            log.debug("user register : [{}].", auser);
        }
        //保存到数据库中
        systemUserService.add(auser);
        if (log.isDebugEnabled()) {
            log.debug("user register : [{}].", auser);
        }
        //注册成功了
        Result result = Result.success(auser);
        if (log.isDebugEnabled()) {
            log.debug("user register : [{}].", auser);
        }
        return result;
    }

    /**
     * 根据注册数据生成系统用户数据
     *
     * @param user
     * @return
     */
    private AdminUser cvtRegisterUser(VoUserRegister user) {
        AdminUser auser = new AdminUser();
        String salt = UUIDUtil.uuidString();
        // 加密密码
        String encodedPassword = this.passwordEncoder.passwordEncode(
                user.getPassword(), salt);
        auser.setEmail(user.getEmail());
        auser.setName(user.getName());
        auser.setUsername(user.getUsername());
        // 存储用户信息，包括 salt 与 hash 后的密码
        auser.setSalt(salt);
        auser.setPassword(encodedPassword);
        LocalDateTime now = LocalDateTime.now();
        auser.setCreatedAt(now);
        auser.setUpdatedAt(now);
        auser.setEnabled(true);
        auser.setPhone(user.getPhone());
        return auser;
    }
}

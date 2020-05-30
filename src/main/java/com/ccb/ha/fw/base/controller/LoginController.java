package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.Result;
import com.ccb.ha.fw.base.annotations.PassToken;
import com.ccb.ha.fw.base.annotations.UserLoginToken;
import com.ccb.ha.fw.base.cache.CacheLoginInfo;
import com.ccb.ha.fw.base.cache.CacheObject;
import com.ccb.ha.fw.base.cache.LoginInfo;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.jwt.JwtConst;
import com.ccb.ha.fw.base.jwt.JwtUtil;
import com.ccb.ha.fw.base.service.AdminUserService;
import com.ccb.ha.fw.base.vo.VoLoginUser;
import com.ccb.ha.fw.util.JsonUtils;
import com.ccb.ha.fw.util.PasswordEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
@Api(value = "用户登录登出")
public class LoginController extends BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(LoginController.class);

    final
    AdminUserService systemUserService;
    final
    JwtConst jwtConst;
    final
    JwtUtil jwtUtil;
    final
    PasswordEncoder passwordEncoder;
    final
    CacheLoginInfo cacheLoginInfo;

    public LoginController(AdminUserService systemUserService,
                           JwtConst jwtConst,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder,
                           CacheLoginInfo cacheLoginInfo) {
        this.systemUserService = systemUserService;
        this.jwtConst = jwtConst;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.cacheLoginInfo = cacheLoginInfo;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/logout")
    @ResponseBody
    @ApiOperation(value = "用户登出")
    @UserLoginToken()
    public Result logout(@RequestHeader HttpHeaders headers) {
        List<String> jwts = headers.get(this.jwtConst.getJwtHeader());
        if (log.isDebugEnabled()) {
            log.debug(" logout action : jwts = [{}]", jwts);
        }
        if ((jwts == null) || (jwts.isEmpty())) {
            String message = "成功登出";
            return Result.success(message);
        }
        String subject = jwts.get(0);
        if (log.isDebugEnabled()) {
            log.debug(" logout action : subject = [{}]", subject);
        }
        String key = this.jwtUtil.getAudience(subject);
        if (log.isDebugEnabled()) {
            log.debug(" logout action : key = [{}]", key);
        }
        //清理
        this.cacheLoginInfo.clean(key);
        String message = "成功登出";
        return Result.success(message);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/login")
    @ResponseBody
    @ApiOperation(value = "用户登录")
    @PassToken()
    public Result login(@RequestBody VoLoginUser requestUser) {
        if (log.isDebugEnabled()) {
            log.debug(" login action : VoLoginUser = [{}]", requestUser);
        }
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //请求信息有误
            String message = "账号或者密码错误";
            if (log.isDebugEnabled()) {
                log.debug(" login action : exception = [message]");
            }
            return Result.error(message);
        }
        AdminUser user = this.systemUserService.login(username,
                username, username);
        if (log.isDebugEnabled()) {
            log.debug(" login action : user = [{}]", user);
        }
        if ((user == null)) {
            //用户不存在
            String message = "账号或者密码错误";
            if (log.isDebugEnabled()) {
                log.debug(" login action : exception = [{}].", message);
            }
            return Result.error(message);
        }
        //加密输入的密码
        String enc = this.passwordEncoder.passwordEncode(
                password, user.getSalt());
        if (!enc.equals(user.getPassword())) {
            //密码不正确
            String message = "账号或者密码错误";
            if (log.isDebugEnabled()) {
                log.debug(" login action : exception = [{}].", message);
            }
            return Result.error(message);
        }
        //用户名称和密码都正确
        String token = this.jwtUtil.getToken(user);
        if (log.isDebugEnabled()) {
            log.debug(" login action : token = [{}]", token);
        }
        LoginInfo info = new LoginInfo(token, user);
        if (log.isDebugEnabled()) {
            log.debug(" login action : info = [{}]", info);
        }
        this.cacheLoginInfo.storeLoginInfo(info);
        return Result.success(token);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/authentication")
    public Result authentication() {
        return Result.success("身份认证成功");
    }
}

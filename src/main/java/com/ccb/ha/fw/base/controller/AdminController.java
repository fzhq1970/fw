package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.Result;
import com.ccb.ha.fw.base.annotations.UserLoginToken;
import com.ccb.ha.fw.base.entity.AdminRole;
import com.ccb.ha.fw.base.entity.AdminRoleMenu;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.service.AdminPermissionService;
import com.ccb.ha.fw.base.service.AdminRoleService;
import com.ccb.ha.fw.base.service.AdminUserRoleService;
import com.ccb.ha.fw.base.service.AdminUserService;
import com.ccb.ha.fw.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/api/admin")
public class AdminController extends BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminUserService adminUserService;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    @ResponseBody
    @UserLoginToken
    public Result listUsers() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("AdminController : get all users.");
        }
        return Result.success(this.adminUserService.listUsers());
    }

    @PutMapping("/user/password")
    @ResponseBody
    @UserLoginToken
    public Result resetPassword(@RequestBody AdminUser requestUser) {
        AdminUser user = adminUserService.login(requestUser.getUsername(),
                requestUser.getUsername(),requestUser.getUsername());
        // 加密密码
        //EncodedPassword encodedPassword = this.passwordEncoder.passwordEncode(user.getPassword());
        //user.setPassword(encodedPassword.getCipher());
        //user.setSalt(encodedPassword.getSalt());
        adminUserService.addOrUpdate(user);
        String message = "重置密码成功";
        return Result.success(message);
    }

    @UserLoginToken
    @PutMapping("/user")
    @ResponseBody
    public Result editUser(@RequestBody AdminUser requestUser) {
        AdminUser user = this.adminUserService.login(requestUser.getUsername(),
                requestUser.getUsername(),requestUser.getUsername());
        user.setName(requestUser.getName());
        user.setPhone(requestUser.getPhone());
        user.setEmail(requestUser.getEmail());
        adminUserService.addOrUpdate(user);
        adminUserRoleService.saveRoleChanges(user.getId(), requestUser.getRoles());
        String message = "修改用户信息成功";
        return Result.success(message);
    }

    @UserLoginToken
    @RequestMapping("/user/delete")
    @ResponseBody
    public Result deleteUser(@RequestBody AdminUser requestUser) {
        if (log.isDebugEnabled()) {
            log.debug("deleteUser : requestUser = [{}].", requestUser);
        }
        this.adminUserRoleService.deleteUserRoles(requestUser.getId());
        this.adminUserService.deleteUser(requestUser.getId());
        String message = "删除用户信息成功";
        return Result.success(message);
    }

    @UserLoginToken
    @RequestMapping("/user/bulkDelete")
    @ResponseBody
    public Result deleteUsers(@RequestBody List<Long> uIds) {
        if (log.isDebugEnabled()) {
            log.debug("deleteUser : deleteUsers = [{}].", uIds);
        }
        for (Long uId : uIds) {
            if (log.isDebugEnabled()) {
                log.debug("deleteUser : uId = [{}].", uId);
            }
            this.adminUserRoleService.deleteUserRoles(uId);
            this.adminUserService.deleteUser(uId);
        }
        String message = String.format("删除[%d]用户信息成功",
                uIds.size());
        return Result.success(message);
    }

    @UserLoginToken
    @GetMapping("/role")
    @ResponseBody
    public Result listRoles() {
        if (log.isDebugEnabled()) {
            log.debug("AdminController : get all roles.");
        }
        return Result.success(this.adminRoleService.listRoles());
    }

    @UserLoginToken
    @PutMapping("/role/status")
    @ResponseBody
    public Result updateRoleStatus(@RequestBody AdminRole requestRole) {
        AdminRole adminRole = adminRoleService.findById(requestRole.getId());
        adminRole.setEnabled(requestRole.getEnabled());
        adminRoleService.addOrUpdate(adminRole);
        String message = "用户" + adminRole.getNameZh() + "状态更新成功";
        return Result.success(message);
    }

    @UserLoginToken
    @PutMapping("/role")
    @ResponseBody
    public Result editRole(@RequestBody AdminRole requestRole) {
        this.adminRoleService.savePermChanges(requestRole);
        String message = "修改角色信息成功";
        return Result.success(message);
    }

    @UserLoginToken
    @PostMapping("/role")
    @ResponseBody
    public Result addRole(@RequestBody AdminRole requestRole) {
        adminRoleService.addOrUpdate(requestRole);
        String message = "添加角色成功";
        return Result.success(message);
    }

    @UserLoginToken
    @GetMapping("/role/perm")
    @ResponseBody
    public Result listPerms() {
        if (log.isDebugEnabled()) {
            log.debug("AdminController : get all perm.");
        }
        return Result.success(this.adminPermissionService.listAllPerms());
    }

    //@RequiresPermissions("/api/admin/role")
    @UserLoginToken
    @PutMapping("/role/menu")
    @ResponseBody
    public Result updateRoleMenu(@RequestParam Long rid, @RequestBody LinkedHashMap menusIds) {
        List<AdminRoleMenu> menus = new ArrayList<>();
        for (Object value : menusIds.values()) {
            for (Long mid : (List<Long>) value) {
                AdminRoleMenu rm = new AdminRoleMenu();
                rm.setId(null);
                rm.setRid(rid);
                rm.setMid(mid);
                menus.add(rm);
            }
        }
        this.adminRoleService.updateRoleMenu(rid, menus);
        return Result.SUCCESS;
    }
}

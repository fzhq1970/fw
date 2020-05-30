package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.Result;
import com.ccb.ha.fw.base.entity.AdminMenu;
import com.ccb.ha.fw.base.service.AdminMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/api/menu")
public class AdminMenuController extends BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(AdminMenuController.class);

    @Autowired
    AdminMenuService adminMenuService;

    /**
     * 获取当前用户的菜单
     *
     * @return
     */
    @RequestMapping(value = "/menus")
    @ResponseBody
    public Result getMenusByCurrentUser() {
        //得到用户名称
        String username = this.getCurrentUsername();
        if (log.isDebugEnabled()) {
            log.debug("AdminMenuController getMenusByUserName: [{}].", username);
        }
        List<AdminMenu> menus = this.adminMenuService.getMenusByUserName(username);
        if (log.isDebugEnabled()) {
            log.debug("AdminMenuController getMenusByUserName menus: [{}].", menus);
        }
        Result result = Result.success(menus);
        return result;
    }
}

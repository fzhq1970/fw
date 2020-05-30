package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.AdminMenuDao;
import com.ccb.ha.fw.base.dao.AdminRoleMenuDao;
import com.ccb.ha.fw.base.dao.AdminUserDao;
import com.ccb.ha.fw.base.dao.AdminUserRoleDao;
import com.ccb.ha.fw.base.entity.AdminMenu;
import com.ccb.ha.fw.base.entity.AdminRoleMenu;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.entity.AdminUserRole;
import com.ccb.ha.fw.base.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminMenuService {
    private static final Logger log =
            LoggerFactory.getLogger(AdminMenuService.class);
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    AdminUserRoleDao adminUserRoleDAO;
    @Autowired
    AdminRoleMenuDao adminRoleMenuDAO;
    @Autowired
    AdminMenuDao adminMenuDAO;

    /**
     * 生成当前用户的功能菜单
     *
     * @return
     */
    public List<AdminMenu> getMenusByUserName(String username) {
        if (log.isDebugEnabled()) {
            log.debug("get menus for user : [{}].", username);
        }
        AdminUser user = this.adminUserDao.findByUsername(username);
        if (log.isDebugEnabled()) {
            log.debug("get menus for user : [{}].", user);
        }
        if (user == null) {
            //没有找到用户，一般不应该，到这里就是除了严重错误
            throw BaseException.EX_UNKNOWN;
        }
        //用户ID
        Long uid = user.getId();
        //所有的用户角色
        List<AdminUserRole> roles = this.adminUserRoleDAO.findDistinctByUidOrderByRid(uid);
        if (log.isDebugEnabled()) {
            log.debug("get menus for user roles : [{}].", roles);
        }
        //通过用户角色获取该用户的菜单ID
        if ((roles == null) || (roles.isEmpty())) {
            //没有任何角色，返回空数据
            return new ArrayList<>();
        }
        //这里需要迭代获取所有角色ID
        List<Long> rids = new ArrayList<>();
        for (AdminUserRole role : roles) {
            rids.add(role.getRid());
        }
        //通过菜单ID获取用户的所有的用户菜单
        return this.genMenu(rids);
    }

    /**
     * 生成角色的功能菜单
     *
     * @return
     */
    public List<AdminMenu> getMenusByRoleId(Long rid) {
        if (log.isDebugEnabled()) {
            log.debug("get menus for rid : [{}].", rid);
        }
        Set<Long> rids = new HashSet<>();
        rids.add(rid);
        List<AdminMenu> trees = this.genMenu(rids);
        if (log.isDebugEnabled()) {
            log.debug("get menus for user trees : [{}].", trees);
        }
        return trees;
    }

    /**
     * 根据角色代码列表，生成这些角色的总体菜单树
     * @param rids
     * @return
     */
    private List<AdminMenu> genMenu(Iterable<Long> rids) {
        //通过菜单ID获取用户的所有的用户菜单
        List<AdminRoleMenu> rms = this.adminRoleMenuDAO.findDistinctByRidIsInOrderByMid(rids);
        if (log.isDebugEnabled()) {
            log.debug("get menus for user rms : [{}].", rms);
        }
        if ((rms == null) || (rms.isEmpty())) {
            //没有任何菜单，返回空数据
            return new ArrayList<>();
        }
        Set<Long> mids = new HashSet<>();
        for (AdminRoleMenu rm : rms) {
            mids.add(rm.getMid());
        }
        if (log.isDebugEnabled()) {
            log.debug("get menus for user menus : [{}].", mids);
        }
        //这里获得所有的菜单
        List<AdminMenu> menus = this.adminMenuDAO.findDistinctByIdIn(mids);
        if (log.isDebugEnabled()) {
            log.debug("get menus for user menus : [{}].", menus);
        }
        //这里需要把所有菜单组成森林结构
        List<AdminMenu> trees = new ArrayList<>();
        AdminMenu son;
        AdminMenu parent;
        for (int i = 0; i < menus.size(); i++) {
            //针对每一个节点，查找他的上级
            son = menus.get(i);
            Long pid = son.getPid();
            if ((pid == null) || (pid.longValue() == 0L)) {
                //根元素，加入到树林中
                trees.add(son);
                continue;
            }
            long pv = pid.longValue();
            for (int j = 0; j < menus.size(); j++) {
                if (i == j) {
                    //跳过自己
                    continue;
                }
                parent = menus.get(j);
                if (parent.getId().longValue() == pv) {
                    //找到上级了
                    parent.addChild(son);
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("get menus for user trees : [{}].", trees);
        }
        return trees;
    }
}

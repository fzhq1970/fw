package com.ccb.ha.fw.base.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLPathMatchingFilter{
    //public class URLPathMatchingFilter extends PathMatchingFilter {
    private static final Logger log =
            LoggerFactory.getLogger(URLPathMatchingFilter.class);
//    @Autowired
//    AdminPermissionService adminPermissionService;
//
//    @Override
//    protected boolean onPreHandle(ServletRequest request, ServletResponse response,
//                                  Object mappedValue) throws Exception {
//        if (log.isDebugEnabled()) {
//            log.debug("onPreHandle : request = [{}}, response = [{}], mappedValue = [{}].",
//                    request, response, mappedValue);
//        }
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        // 放行 options 请求，这是为了跨域使用
//        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
//            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
//            return true;
//        }
//        //解决Shiro 的一个bug
//        if (null == adminPermissionService) {
//            adminPermissionService = SpringContextUtils.getBean(AdminPermissionService.class);
//        }
//
//        String requestAPI = getPathWithinApplication(request);
//        if (log.isDebugEnabled()) {
//            log.debug("onPreHandle 访问接口：{}", requestAPI);
//        }
//
//        //当前用户信息
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.isAuthenticated()) {
//            if (log.isDebugEnabled()) {
//                log.debug("onPreHandle 访问接口：用户需要登录。");
//            }
//            return false;
//        }
//
//        // 判断访问接口是否需要过滤（数据库中是否有对应信息）
//        boolean needFilter = adminPermissionService.needFilter(requestAPI);
//        if (!needFilter) {
//            if (log.isDebugEnabled()) {
//                log.debug("onPreHandle 访问接口：{} 无需权限。", requestAPI);
//            }
//            return true;
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("onPreHandle 访问接口：{} 需权限。", requestAPI);
//        }
//        // 判断当前用户是否有相应权限
//        String username = subject.getPrincipal().toString();
//        Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(username);
//        for (String api : permissionAPIs) {
//            if (api.equals(requestAPI)) {
//                if (log.isDebugEnabled()) {
//                    log.debug("onPreHandle 访问接口：{} 验证成功。", requestAPI);
//                }
//                return true;
//            }
//        }
//        //无权使用
//        if (log.isDebugEnabled()) {
//            log.debug("onPreHandle 访问接口：{} 权限验证失败。", requestAPI);
//        }
//        return false;
//    }
//        return true;
//    }
}

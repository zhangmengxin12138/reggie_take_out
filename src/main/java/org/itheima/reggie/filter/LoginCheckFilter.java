package org.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 检查用户是否已经登陆
* */
@Slf4j
@WebFilter(filterName="loginCheckFilter",urlPatterns = "/*")

/*
* 定义拦截器
* */
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String requestURI = request.getRequestURI();
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "front/**"
        };
        boolean check = check(urls, requestURI);
        if (check) {
            log.info("未限制资源");
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employee")!=null) {
            log.info("已登陆放行");
            filterChain.doFilter(request,response);
            return;
        }

        log.info("返回错误标识");
           response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }
/*
* 地址匹配方法
* */
    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }

        }
        return false;
    }
}

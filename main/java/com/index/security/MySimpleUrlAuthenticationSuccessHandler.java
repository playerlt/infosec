package com.index.security;

import com.index.dao.UserMapper;
import com.index.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author index
 * @date 2021/6/21
 **/
@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserMapper userMapper;

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//        if (roles.contains("ROLE_ADMIN")) {
//            response.sendRedirect(basePath + "adminHome");
//            return;
//        }

//        ModelAndView model  =  new ModelAndView();
//        String name = authentication.getName();
//
//        model.addObject("username", name);

        response.sendRedirect(basePath);

    }
}

package com.index.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.index.dao.UserMapper;
import com.index.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class WeChatAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserMapper userMapper;

    /**
     * 这里的用户名是js_code
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        System.out.println("用户证书登录");

        //私钥加密后的用户名
        String username = (String)authentication.getPrincipal();
        //经过CA认证的公钥
        String key = (String)authentication.getCredentials();

        RSA rsa = new RSA(null, key);
        byte[] decrypt = rsa.decrypt(Base64.decode(username), KeyType.PublicKey);
        username = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        User user = userMapper.getUserByUserName(username);
        if(user == null)
            throw new CredentialsExpiredException("证书认证错误");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRolecode());
        authorities.add(authority);
        WeChatAuthenticationToken token = new WeChatAuthenticationToken(user.getUsername(), null, authorities);
//        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return WeChatAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

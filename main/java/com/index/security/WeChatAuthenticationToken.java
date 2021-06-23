package com.index.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;


public class WeChatAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -6231962326068951783L;

    public WeChatAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public WeChatAuthenticationToken(Object principal, Object credentials,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }



}




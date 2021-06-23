package com.index.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author index
 * @date 2021/6/22
 **/
@Component
public class CusAuthenticationManager implements AuthenticationManager {

    private final WeChatAuthenticationProvider authenticationProvider;

    public CusAuthenticationManager(WeChatAuthenticationProvider adminAuthenticationProvider) {
        this.authenticationProvider = adminAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = authenticationProvider.authenticate(authentication);
        if (Objects.nonNull(result)) {
            return result;
        }
        throw new ProviderNotFoundException("Authentication failed!");
    }
}

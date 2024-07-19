package com.assessment.bank.serverapp.configs;

import com.assessment.bank.serverapp.models.AppUserDetail;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuthenticationConfig {

    public AppUserDetail getLoginUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (AppUserDetail) auth.getPrincipal();
    }
}

package com.glqdlt.sso.grantserver.app.secu;

import com.glqdlt.sso.grantserver.entity.UserEntityRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Jhun
 * 2020-02-06
 */
public class MyUserDetailService implements UserDetailsService {
    private UserEntityRepo userEntityRepo;

    public MyUserDetailService(UserEntityRepo userEntityRepo) {
        this.userEntityRepo = userEntityRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepo.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException(String.format("%s not founded user", username)));
    }
}

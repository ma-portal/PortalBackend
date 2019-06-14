package org.luncert.portal.component;

import org.luncert.portal.model.mongo.User;
import org.luncert.portal.model.mongo.User.Role;
import org.luncert.portal.repos.mongo.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */

    @Override
    public UserDetails loadUserByUsername(String account)
    {
        User user = userRepos.findByAccount(account);
        Objects.requireNonNull(user, "invalid account: " + account);

        List<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.toString()));
            }
        }

        return new org.springframework.security.core.userdetails.User(
            user.getAccount(), passwordEncoder.encode(user.getPassword()), authorities);
    }

}
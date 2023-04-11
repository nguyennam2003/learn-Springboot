package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.entity.User;
import jmaster.io.btvnproject3.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User st = userRepo.findByUsername(username);

        if (st == null)
            throw new UsernameNotFoundException("Not found");

        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

        for (String role : st.getRoles())
            list.add(new SimpleGrantedAuthority(role));

        //tao user cua security
        //user dang nhap hien tai
        org.springframework.security.core.userdetails.User currentUser =
                new org.springframework.security.core.userdetails.User(username, st.getPassword(), list);

        return currentUser;
    }


}

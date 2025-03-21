package com.loquierestecno.loquierestecnoBack.service;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.loquierestecno.loquierestecnoBack.model.Usuario;
import com.loquierestecno.loquierestecnoBack.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Authenticate a user from the database.
 */
@Component
@RequiredArgsConstructor
@Transactional
public class MyUserDetailsService implements UserDetailsService  {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        String lowercaseLogin = correo; // login.toLowerCase();
        Optional<Usuario> user = usuarioRepository.findByCorreo(correo);
        if (!user.isPresent())
            throw new UsernameNotFoundException("Not found");

        Usuario userFromDatabase = user.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(userFromDatabase.getRol().name()));

        return
                new User(lowercaseLogin, userFromDatabase.getContrasena(),
                        userFromDatabase.isEnabled(),    // enabled
                        userFromDatabase.isAccountNonExpired(),                   // accountNonExpired
                        userFromDatabase.isCredentialsNonExpired(),         // credentialsNonExpired
                        userFromDatabase.isAccountNonLocked(), // accountNonLocked
                        grantedAuthorities);
    }
}

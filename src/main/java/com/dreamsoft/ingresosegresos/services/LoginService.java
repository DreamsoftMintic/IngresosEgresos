package com.dreamsoft.ingresosegresos.services;

import com.dreamsoft.ingresosegresos.entities.Empleado;
import com.dreamsoft.ingresosegresos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("UserDetailsService")
public class LoginService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Empleado usuario = usuarioRepository.findByUsername(username);
        if(usuario == null){
            throw  new UsernameNotFoundException(username);
        }
        var roles = new ArrayList< GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(usuario.getRol())); //no se puso el .getDrescripcion()

        return new User(usuario.getUsername(),usuario.getPass(),roles);
    }

}

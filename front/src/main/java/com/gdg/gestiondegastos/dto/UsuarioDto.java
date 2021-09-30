/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Contactos;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.TokenEntity;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto implements UserDetails{

    private Integer id;
    
    private String correo;
    private String contrasenya;
    private List<UsuarioGrupo> usuarioGrupo;
    private double presupuestoPersonal;
    private List<Movimiento> movimientos;
    private List<Contactos> contactos;
    private TokenEntity token;
    private List<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
    
    public UsuarioDto(Usuario usu){
        id=usu.getId();
        correo=usu.getCorreo();
        contrasenya=usu.getContrasenya();
        roles.add(new SimpleGrantedAuthority("ROLE_Usuario"));
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return contrasenya;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}

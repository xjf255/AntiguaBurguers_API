package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.dto.LoginUsuarioClienteDTO;
import com.project.antiguaburguers.dto.SignInUsuarioClienteDTO;
import com.project.antiguaburguers.model.UsuarioCliente;

public interface UsuarioMapper {
    LoginUsuarioClienteDTO toLoginDTO(UsuarioCliente usuarioCliente);
    SignInUsuarioClienteDTO toSignInDTO(UsuarioCliente usuarioCliente);
    UsuarioCliente toUsuarioEntity(SignInUsuarioClienteDTO loginUsuarioClienteDTO);
    UsuarioCliente toUsuarioEntity(LoginUsuarioClienteDTO loginUsuarioClienteDTO);
}

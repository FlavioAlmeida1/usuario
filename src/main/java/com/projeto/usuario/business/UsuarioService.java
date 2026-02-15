package com.projeto.usuario.business;

import com.projeto.usuario.business.converter.UsuarioConverter;
import com.projeto.usuario.business.dto.UsuarioDTO;
import com.projeto.usuario.infraestructure.entity.Usuario;
import com.projeto.usuario.infraestructure.exception.ConflictException;
import com.projeto.usuario.infraestructure.exception.ResourceNotFoundException;
import com.projeto.usuario.infraestructure.repository.TelefoneRepository;
import com.projeto.usuario.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; // <= Injeção de dependência.
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;


    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExite(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );

    }
    public void emailExite(String email){
        try{
            boolean existe = verificaEmailExiste(email);
            if(existe){
                throw new ConflictException("Email já cadastrado! " + email);
            }
        } catch(Exception e){
            throw new ConflictException("email já cadastrado!", e.getCause());
        }
    }

    public boolean verificaEmailExiste(String email){
        return usuarioRepository.existsByEmail(email);
    }
    public Usuario buscarUsuarioPorEmail(String email){
       return usuarioRepository.findByEmail(email).orElseThrow(
               () -> new ResourceNotFoundException("Email não encontrado! " + email));
    }
    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}

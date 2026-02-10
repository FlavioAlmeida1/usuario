package com.projeto.usuario.business.converter;

import com.projeto.usuario.business.dto.EnderecoDTO;
import com.projeto.usuario.business.dto.TelefoneDTO;
import com.projeto.usuario.business.dto.UsuarioDTO;
import com.projeto.usuario.infraestructure.entity.Endereco;
import com.projeto.usuario.infraestructure.entity.Telefone;
import com.projeto.usuario.infraestructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListarEndereco(usuarioDTO.getEnderecos()))
                .telefones(paralistartelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListarEndereco(List<EnderecoDTO> enderecoDTOS) {
        //return enderecoDTOS.stream().map(this::paraEndereco).toList(); forma mais avançada.
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paralistartelefones(List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

//**************Convertendo para dto************************************
public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
    return UsuarioDTO.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(usuarioDTO.getSenha())
            .enderecos(paraListarEnderecoDTO(usuarioDTO.getEnderecos()))
            .telefones(paralistartelefonesDTO(usuarioDTO.getTelefones()))
            .build();
}

    public List<EnderecoDTO> paraListarEnderecoDTO(List<Endereco> enderecoDTOS) {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO) {
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<TelefoneDTO> paralistartelefonesDTO(List<Telefone> telefoneDTO) {
        return telefoneDTO.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }


}
/* Em um padrao normal sem usar o "@Builder", seria criado o codigo dessa forma.
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
       Usuario usuario = new Usuario();
       usuario.setEmail(usuarioDTO.getEmail());
       usuario.setNome(usuarioDTO.getNome());
    }
   Não é errado fazer dessa forma, porem a utilização do builder vai permitir que não seja
   criado mto codigo.

   public List<Endereco> paraListarEndereco(List<EnderecoDTO> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();

   Existe uma outra opção para fazer a listagem dos endereços utilizando a função "for".
   public List<Endereco> paraListarEndereco(List<EnderecoDTO> enderecoDTOS){
                List<Endereco> enderecos = new ArrayList<>();
        for(EnderecoDTO enderecoDTO : enderecoDTOS){
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;

 */

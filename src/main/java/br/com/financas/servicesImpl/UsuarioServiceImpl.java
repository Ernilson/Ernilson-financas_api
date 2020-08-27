package br.com.financas.servicesImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.financas.Exeptions.ErroAuthentication;
import br.com.financas.Exeptions.RegraNegocioException;
import br.com.financas.model.Usuario;
import br.com.financas.repositories.UsuarioRepository;
import br.com.financas.services.UsuarioServices;

@Service
public class UsuarioServiceImpl implements UsuarioServices {

	private UsuarioRepository usuarioRep;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRep) {
		super();
		this.usuarioRep = usuarioRep;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = usuarioRep.findByEmail(email);

		if (!usuario.isPresent()) {
			throw new ErroAuthentication("Usuario não encontrado para o email informado!");
		}
		
		else if (!usuario.get().getSenha().equals(senha)) {
			throw new ErroAuthentication("Senha invalida!");
		}

		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {
		ValidarEmail(usuario.getEmail());
		return usuarioRep.save(usuario);
	}

	@Override
	public void ValidarEmail(String email) {
		boolean existe = usuarioRep.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException("Já existe um usuario com este email");
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
	return usuarioRep.findById(id);
	
	}

	@Override
	public Optional<Usuario> findByOne(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}
     // Metodo para buscar usuario por id
        public Usuario getOne(Long id) {
            return usuarioRep.getOne(id);
        }    
	

}

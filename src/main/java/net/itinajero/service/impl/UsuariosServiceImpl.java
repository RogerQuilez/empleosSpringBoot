package net.itinajero.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.itinajero.model.Usuario;
import net.itinajero.repository.IUsuariosRepository;
import net.itinajero.service.IUsuariosService;

@Service
public class UsuariosServiceImpl implements IUsuariosService {
	
	@Autowired
	private IUsuariosRepository usuariosRepo;

	@Override
	public void guardar(Usuario usuario) {
		usuariosRepo.save(usuario);		
	}

	@Override
	public void eliminar(Integer idUsuario) {
		usuariosRepo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		List<Usuario> users = usuariosRepo.findAll();
		return users;
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuariosRepo.findByUsername(username);
	}

}

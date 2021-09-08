package net.itinajero.service;

import java.util.List;

import net.itinajero.model.Usuario;

public interface IUsuariosService {
	
	public void guardar(Usuario usuario);
	public void eliminar(Integer idUsuario);
	public List<Usuario> buscarTodos();
	public Usuario findByUsername(String username);
}

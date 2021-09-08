package net.itinajero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.itinajero.model.Usuario;

public interface IUsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	public Usuario findByUsername(String username);

}

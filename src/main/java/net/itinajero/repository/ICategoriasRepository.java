package net.itinajero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.itinajero.model.Categoria;

//public interface ICategoriasRepository extends CrudRepository<Categoria, Integer> {
public interface ICategoriasRepository extends JpaRepository<Categoria, Integer> {

}

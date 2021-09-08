package net.itinajero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.itinajero.model.Vacante;

public interface IVacantesRepository extends JpaRepository<Vacante, Integer> {

	public List<Vacante> findByEstatus(String estatus);
	public List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado, String estatus);
	public List<Vacante> findBySalarioBetweenOrderBySalarioAsc(Double min, Double max);
	public List<Vacante> findByEstatusIn(String[] estatus);
}

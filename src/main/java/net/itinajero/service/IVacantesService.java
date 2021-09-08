package net.itinajero.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Vacante;

public interface IVacantesService {

	public List<Vacante> buscarTodas();
	public Vacante findById(int id);
	public void guardar(Vacante vacante);
	public List<Vacante> findDestacadas();
	public void delete(Integer idVacante);
	public List<Vacante> buscarByExample(Example<Vacante> example);
	public Page<Vacante> buscarTodas(Pageable page);
	
}

package net.itinajero.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.Vacante;
import net.itinajero.repository.IVacantesRepository;
import net.itinajero.service.IVacantesService;

@Service
public class VacantesServiceImpl implements IVacantesService {
	
	@Autowired
	 private IVacantesRepository vacantesRepository;

	@Override
	public List<Vacante> buscarTodas() {
		return vacantesRepository.findAll();
	}

	@Override
	public Vacante findById(int id) {
		Optional<Vacante> vacante = vacantesRepository.findById(id);
		if (vacante.isPresent()) return vacante.get();
		return null;		
	}

	@Override
	public void guardar(Vacante vacante) {
		vacantesRepository.save(vacante);
	}

	@Override
	public List<Vacante> findDestacadas() {
		return vacantesRepository.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void delete(Integer idVacante) {
		vacantesRepository.deleteById(idVacante);		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return vacantesRepository.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return vacantesRepository.findAll(page);
	}
	
}

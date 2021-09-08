package net.itinajero.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.Categoria;
import net.itinajero.repository.ICategoriasRepository;
import net.itinajero.service.ICategoriasService;

@Service
public class CategoriasServiceImpl implements ICategoriasService {
	
	@Autowired
	private ICategoriasRepository categoriasRepository;
	
	@Override
	public void guardar(Categoria categoria) {
		categoriasRepository.save(categoria);	
	}

	@Override
	public List<Categoria> buscarTodas() {
		return categoriasRepository.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> categoria = categoriasRepository.findById(idCategoria);
		if (categoria.isPresent()) return categoria.get();
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		categoriasRepository.deleteById(idCategoria);
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		return categoriasRepository.findAll(page);
	}

}

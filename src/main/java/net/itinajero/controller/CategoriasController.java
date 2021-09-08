package net.itinajero.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Categoria;
import net.itinajero.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	private ICategoriasService categoriasService;

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> listCategoria = categoriasService.buscarTodas();
		model.addAttribute("categorias", listCategoria);
		return "categorias/listCategorias";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista = categoriasService.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) return "categorias/formCategoria";
		categoriasService.guardar(categoria);
		attributes.addFlashAttribute("msg", "Registro Guardado Correctamente");
		return "redirect:/categorias/index";
	}	
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer idCategoria, RedirectAttributes attributes) {
		
		try {
			categoriasService.eliminar(idCategoria);
			attributes.addFlashAttribute("msg", "Registro Eliminado Correctamente");
		} catch (Exception e) {
			attributes.addFlashAttribute("msg", "No se pudo eliminar la Categoria");
		}
		
		return "redirect:/categorias/index";
	}
	
	@GetMapping("update/{id}")
	public String update(@PathVariable("id") Integer idCategoria, Model model) {
		Categoria cat = categoriasService.buscarPorId(idCategoria);
		model.addAttribute("categoria", cat);
		return "categorias/formCategoria";
	}
	
}

package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IVacantesService;
import net.itinajero.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}") /*Inyecta el valor de la propiedad en la variable ruta */
	private String ruta;
	
	@Autowired
	private IVacantesService vacanteService;
	
	@Autowired
	private ICategoriasService categoriasService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> listVacante = vacanteService.buscarTodas();
		model.addAttribute("vacantes", listVacante);
		
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = vacanteService.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		
		if (result.hasErrors()) return "vacantes/formVacante"; /* Comprueba los posibles errores del formulario */
		
		/* Subir una imagen de Vacante */
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) {
				vacante.setImagen(nombreImagen);
			}
		}
		
		vacanteService.guardar(vacante);
		System.out.println(vacante);
		attributes.addFlashAttribute("msg", "Registro Guardado Correctamente"); /* Atributos que se pueden mandar con el redirect */
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {

		vacanteService.delete(idVacante);
		attributes.addFlashAttribute("msg", "Vacante eliminada correctamente");
		return "redirect:/vacantes/index";
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		
		Vacante vacante = vacanteService.findById(idVacante);
		model.addAttribute("vacante", vacante);
		
		//Buscar los detalles de la vacante en la BD
		
		return "vacantes/detalle";
		
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = vacanteService.findById(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";
	}
	
	@ModelAttribute /* Para añadir datos a models en común */
	public void setGenericos(Model model) {
		model.addAttribute("categorias", categoriasService.buscarTodas());
	}
	
	/* Personalización para el tipo Date que entra en el Binding de Spring */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
}

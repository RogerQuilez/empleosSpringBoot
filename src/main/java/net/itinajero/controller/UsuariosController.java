package net.itinajero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Usuario;
import net.itinajero.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private IUsuariosService usuariosService;
    
    @GetMapping("/index")
	public String mostrarIndex(Model model) {

    	List<Usuario> usuarios = usuariosService.buscarTodos();
    	model.addAttribute("usuarios", usuarios);
    	
    	return "usuarios/listUsuarios";
	}
    
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		    	
		
    	try {
    		usuariosService.eliminar(idUsuario);
    		attributes.addFlashAttribute("msg", "Registro Eliminado Correctamente");
    	} catch (Exception e) {
    		attributes.addFlashAttribute("msg", "No se pudo eliminar el registro correctamente");
    	}
    	
		return "redirect:/usuarios/index";
	}
}

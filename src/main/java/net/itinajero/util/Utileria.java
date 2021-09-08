package net.itinajero.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {
	
	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		
		String nombreOriginal = multiPart.getOriginalFilename();
		nombreOriginal.replace(" ", "-");
		String nombreFinal = randomAlphaNumeric(8) + nombreOriginal;
		
		try {
			
			File imageFile = new File(ruta + nombreFinal);
			System.out.println("Archivo " + imageFile.getAbsolutePath());
			multiPart.transferTo(imageFile);
			return nombreFinal;
			
		} catch (IOException e) {
			
			System.out.println("Error al guardar el archivo " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Metodo para generar una cadena aleatoria de longitud N
	 * @param count
	 * @return
	 */
	public static String randomAlphaNumeric(int count) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRASTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		return builder.toString();
	}
	
}

package net.itinajero.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DataWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	/* Bean para encriptar contraseñas */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/* Configurar el Login de Usuarios para Spring Security */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("SELECT username, password, estatus FROM usuarios WHERE username = ?")
			.authoritiesByUsernameQuery("SELECT u.username, p.perfil FROM usuarioperfil up " +
			"INNER JOIN usuarios u on u.id = up.idUsuario " + 
			"INNER JOIN perfiles p on p.id = up.idPerfil " +
			"WHERE u.username = ?");
	}
	
	/* Metodo para autorizar a las URLS correspondientes */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		
		/* Los recursos estáticos no requieren authenticación */
		.antMatchers(
				"/bootstrap/**",
				"/images/**",
				"/tinymce/**",
				"/logos/**").permitAll()
		
		/* Las vistas publicas no requieren autenticación */
		.antMatchers("/",
				"/signup",
				"/search",
				"/bcrypt/**",
				"/vacantes/view/**").permitAll()
		
		/* Asignar permisos a URLs por ROLES */
		.antMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
		
		/* Todas las demás URLS requieren autenticación */
		.anyRequest().authenticated()
		
		/* El formulario del login no requiere autenticación */
		.and().formLogin().loginPage("/login").permitAll();
		
	}

}

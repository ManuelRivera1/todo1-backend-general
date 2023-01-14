package com.general.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.general.dto.AutenticacionRespuestaDTO;
import com.general.dto.registrarUsuariosDTO;
import com.general.service.AccesoUsuarioService;
import com.general.util.ExcepcionComercial;
import com.general.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import static org.reflections.Reflections.log;
/**
 * Servicio que contiene todos los procesos de negocio para la autenticacion
 * localhost:puerto/auth/
 */
@RestController
@RequestMapping("/acceso-general")
public class AccesoUsuarioResource {

	/** Service para que contiene los procesos de negocio para la autenticacion */
	@Autowired
	private AccesoUsuarioService accesoUsuarioService;

	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos del response para la autenticacion en el sistema
	 */
	@PostMapping(path = "/validarExisteUsuario")
	@ApiOperation(value = "Iniciar Sesion", notes = "Operación para iniciar sesión en el sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> iniciarSesion(@RequestBody AutenticacionRespuestaDTO credenciales) {
		try {
			log.info("INFORMACION iniciarSesion: "+credenciales);
			return Util.getResponseSuccessful(this.accesoUsuarioService.iniciarSesion(credenciales));
		} catch (ExcepcionComercial e) {
			log.error("ERROR iniciarSesion: "+e.getMessage());
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			log.error("ERROR iniciarSesion: "+e.getMessage());
			return Util.getResponseError(AccesoUsuarioResource.class.getSimpleName() + ".iniciarSesion ", e.getMessage());
		}
	}
	
	/**
	 * Servicio que soporta la incersion de datos en usuarios
	 */
	@PostMapping(path = "/crearUsuario")
	@ApiOperation(value = "crear usuarios", notes = "Operación para crear usuarios ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> crearUsuarios(@RequestBody registrarUsuariosDTO credenciales) {
		try {
			log.info("INFORMACION iniciarSesion: "+credenciales);
			return Util.getResponseSuccessful(this.accesoUsuarioService.crearUsuarios(credenciales));
		} catch (ExcepcionComercial e) {
			log.error("ERROR iniciarSesion: "+e.getMessage());
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			log.error("ERROR iniciarSesion: "+e.getMessage());
			return Util.getResponseError(AccesoUsuarioResource.class.getSimpleName() + ".crearUsuarios ", e.getMessage());
		}
	}


}

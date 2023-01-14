package com.general.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.general.constant.MensajesNegocioClaves;
import com.general.constant.Numero;
import com.general.constant.UrlMicrosSolicitud;
import com.general.dto.AutenticacionRespuestaDTO;
import com.general.dto.registrarUsuariosDTO;
import com.general.http.GenericoHttp;
import com.general.util.ExcepcionComercial;
import com.general.util.Util;

/**
 * Service para que contiene los procesos de negocio para la autenticacion
 */
@Service
@SuppressWarnings("unchecked")
public class AccesoUsuarioService {

	@Autowired
	private GenericoHttp generic;

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el
	 * sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos del response para la autenticacion en el sistema
	 * @throws Exception
	 */
	public List<Object> iniciarSesion(AutenticacionRespuestaDTO credenciales) throws Exception {
		if (credenciales != null && !Util.isNull(credenciales.getClaveIngreso())
				&& !Util.isNull(credenciales.getUsuarioIngreso())) {

			// se consulta el identificador del usuario que coincida con la clave-usuario
			Object data = llamadoConsulta(credenciales);

			if (data != null) {
				String codificadoPsw = (String) ((List<?>) data).get(Numero.DOS.valueI);
				if(verificarClave(credenciales.getClaveIngreso(),codificadoPsw)) {	
					return (List<Object>) data;
				}
				
			}
		
		}
		throw new ExcepcionComercial(MensajesNegocioClaves.KEY_AUTENTICACION_FALLIDA.value);
	}
	
	/**
	 * se encarga de verificar la clave*/
	public Boolean verificarClave(String clave,String codificada){
		if (passwordEncoder.matches(clave, codificada)) {
			return true;
		}
		return false;
	}
	
	private Object llamadoConsulta(AutenticacionRespuestaDTO credenciales) throws Exception {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.ACCESODATOSFEDERACIONACCESOUSUARIOS+"obtenerAuteticacionUsuario?usuarioIngreso={0}";
		uri = Util.buildUri(url, credenciales.getUsuarioIngreso());
		response = this.generic.getService(uri, List.class);
		List<Object> result = (List<Object>) response.getBody();
		// se verifica que si exista el usuario
		if (result != null && !result.isEmpty()) {
			return result.get(Numero.ZERO.valueI);
		}
		return null;

	}
	
	public boolean registrarUsuario(registrarUsuariosDTO credenciales) throws Exception {
		ResponseEntity<?> response;
		boolean body;
		URI uri;
        String url = UrlMicrosSolicitud.ACCESODATOSFEDERACIONACCESOUSUARIOS+"insertarUsuario?usuarioIngreso={0}&clave={1}";
        String clave = passwordEncoder.encode(credenciales.getClave());
        List<String> params = new ArrayList<String>();
		params.add(credenciales.getNombre());
		params.add(clave);
		String[] array = params.toArray(new String[1]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, Object.class);
			body = (boolean) response.getBody();
			if (response.getStatusCode() == HttpStatus.OK && !ObjectUtils.isEmpty(body)) {
				return body;
			}
		} catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return false;
	}
	
	/**
	 * metodo que retorna la excepcion
	 * @param msj que es mensaje de excepcion
	*/
	private String returnExcepcionComercial(String msj) {
		String[] parts = msj.split(":");
		return parts[1].substring(0, parts[1].length()-2).replace('"', ' ').trim();
	}
	
	public Object crearUsuarios(registrarUsuariosDTO credenciales) throws Exception {
		// se consulta el identificador del usuario que coincida con la clave-usuario
		if(registrarUsuario(credenciales)) {
			return true;			
		}
		throw new ExcepcionComercial(MensajesNegocioClaves.KEY_CREATE_USER.value);
	}

}

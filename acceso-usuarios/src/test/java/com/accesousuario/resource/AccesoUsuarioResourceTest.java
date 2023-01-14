package com.accesousuario.resource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.general.dto.AutenticacionRespuestaDTO;
import com.general.resource.AccesoUsuarioResource;
import com.general.util.ExcepcionComercial;

@SpringBootTest
class AccesoUsuarioResourceTest {

	@Autowired
	AccesoUsuarioResource accesoUsuarioResource;
	
	private static ResponseEntity<Object> responseEntity =  null ;
	@Test
	void testIniciarSesion() {
		AutenticacionRespuestaDTO autenticacionRespuestaDTO = new AutenticacionRespuestaDTO();
		autenticacionRespuestaDTO.setClaveIngreso("10101022");
		autenticacionRespuestaDTO.setIdAplicacion(2);
		autenticacionRespuestaDTO.setUsuarioIngreso("10101022");
		responseEntity= accesoUsuarioResource.iniciarSesion(autenticacionRespuestaDTO);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.OK,responseEntity.getStatusCode())
		);
	}
	@Test
	void testIniciarSesionFalla() {
		AutenticacionRespuestaDTO autenticacionRespuestaDTO = new AutenticacionRespuestaDTO();
		autenticacionRespuestaDTO.setClaveIngreso("10101022");
		autenticacionRespuestaDTO.setIdAplicacion(2);
		autenticacionRespuestaDTO.setUsuarioIngreso("101010222");
		responseEntity= accesoUsuarioResource.iniciarSesion(autenticacionRespuestaDTO);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode())
		);
	}
	

	@Test
	void testVerificarClave() throws ExcepcionComercial {
		String clave = "ADATACENTER10";
		String codificada = "$2a$10$v6SpFU3Sjide4cW7AidZ9emMjzkYpZ6iTiX3jam46TJcYPVd0edOC";
		responseEntity= accesoUsuarioResource.verificarClave(clave,codificada);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(true,responseEntity.getBody())
		);
	}
	@Test
	void testVerificarClaveIncorrecta() throws ExcepcionComercial {
		String clave = "ADATACENTER102";
		String codificada = "$2a$10$v6SpFU3Sjide4cW7AidZ9emMjzkYpZ6iTiX3jam46TJcYPVd0edOC";
		responseEntity= accesoUsuarioResource.verificarClave(clave,codificada);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(false,responseEntity.getBody())
		);
	}
}

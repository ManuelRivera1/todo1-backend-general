package com.accesousuario.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.general.constant.MensajesNegocioClaves;
import com.general.dto.AutenticacionRespuestaDTO;
import com.general.service.AccesoUsuarioService;
import com.general.util.ExcepcionComercial;

@SpringBootTest
class AccesoUsuarioServiceTest {
	@Autowired
	AccesoUsuarioService accesoUsuarioService;
	
	private static List<Object> datos = null;
	private static Boolean datosEstado = null;
	@Test
	void testIniciarSesion() throws Exception {
		AutenticacionRespuestaDTO autenticacionRespuestaDTO = new AutenticacionRespuestaDTO();
		autenticacionRespuestaDTO.setClaveIngreso("10101022");
		autenticacionRespuestaDTO.setIdAplicacion(2);
		autenticacionRespuestaDTO.setUsuarioIngreso("10101022");
		datos = accesoUsuarioService.iniciarSesion(autenticacionRespuestaDTO);
		assertAll("resultado",
				() -> assertNotNull(datos),
				() -> assertTrue(datos.size()>=8)
		);
	}
	@Test
	void testIniciarSesionFalla() throws ExcepcionComercial {
		AutenticacionRespuestaDTO autenticacionRespuestaDTO = new AutenticacionRespuestaDTO();
		autenticacionRespuestaDTO.setClaveIngreso("10101022");
		autenticacionRespuestaDTO.setIdAplicacion(2);
		autenticacionRespuestaDTO.setUsuarioIngreso("101010222");
		ExcepcionComercial thrown = Assertions.assertThrows(ExcepcionComercial.class, ()-> accesoUsuarioService.iniciarSesion(autenticacionRespuestaDTO)); 
		assertEquals(MensajesNegocioClaves.KEY_AUTENTICACION_FALLIDA.value, thrown.getMessage());
	}

	@Test
	void testVerificarClave() {
		String clave = "ADATACENTER10";
		String codificada = "$2a$10$v6SpFU3Sjide4cW7AidZ9emMjzkYpZ6iTiX3jam46TJcYPVd0edOC";
		datosEstado = accesoUsuarioService.verificarClave(clave,codificada);
		assertAll("resultado",
				() -> assertNotNull(datosEstado),
				() -> assertEquals(true,datosEstado)
		);
	}

}

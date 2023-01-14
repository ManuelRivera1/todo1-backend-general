package com.general.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para encapsular los datos que se envia desde el cliente
 * (angular, app movil) al momento de la autenticacion del sistema
 */
@Getter
@Setter
@NoArgsConstructor
public class AutenticacionRespuestaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es la clave de ingreso al sistema */
	private String claveIngreso;

	/** Es el usuario de ingreso al sistema */
	private String usuarioIngreso;

}

package com.general.constant;

import lombok.experimental.UtilityClass;

/**
 * Clase constante que contiene los estados posibles del sistema
 */
@UtilityClass
public class Estado {
	public static final String ACTIVO = "ACTIVO";
	public static final String INACTIVO = "INACTIVO";
	public static final String ASIGNADO = "ASIGNADO";
	public static final Integer SMS_ACTIVO = 1;
	public static final Integer SMS_INACTIVO = 0;
}

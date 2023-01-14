package com.general.constant;

import lombok.experimental.UtilityClass;

/**
 * Clase constante que contiene los identificadores de las aplicaciones
 */
@UtilityClass
public class Constants {

	/** Identificadores de los tipos de aplicacion */
	public static final Integer ID_APLICACION_BACKOFFICE = 1;
	public static final Integer ID_APLICACION_VENTAS = 2;
	public static final Integer ID_APLICACION_MAQUINA_MOVIL=4;

	/** Identificador del rol administrador */
	public static final String ID_ADMINISTRADOR = Numero.DOS.valueI.toString();

	/** Delimitador de coma */
	public static final String COMA = ",";
	
	/** Mensaje para sms de twilio*/
	public static final String SMS_TWILIO = "Tu código de validación para el registro en logii es ";
}

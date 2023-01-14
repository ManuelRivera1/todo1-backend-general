package com.general.constant;

/**
 * Se debe mandar el codigo del business messages al cliente (angular, movil)
 */
public enum MensajesNegocioClaves {

	/** 400 - Credenciales incorrectas. Inténtalo de nuevo. */
	KEY_AUTENTICACION_FALLIDA("security-0001"),
	KEY_PROGRAMACION_NULA("security-0002"),
	KEY_LOGIN_YA_EXISTE("admin-0022"),
	KEY_PAPELERIA_USUARIO("admin-0025"),
	KEY_EMPRESA_USUARIO("admin-0026"),
	KEY_USUARIO_NO_ACTIVO("security-0003"),
	// Rol de usuario no se encuentra activo
	KEY_ROL_USUARIO_NO_ACTIVO("security-0004"),
	// SMS
	KEY_SMS_ERROR("sms-0001"),
	KEY_SMS_VERIFICA("sms-0002"),
	KEY_VOICE_ERROR("voice-001");

	public final String value;
	private MensajesNegocioClaves(String value) {
		this.value = value;
	}
}

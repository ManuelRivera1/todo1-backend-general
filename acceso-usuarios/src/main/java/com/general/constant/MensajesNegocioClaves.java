package com.general.constant;

/**
 * Se debe mandar el codigo del business messages al cliente (angular, movil)
 */
public enum MensajesNegocioClaves {

	/** 400 - Credenciales incorrectas. Inténtalo de nuevo. */
	KEY_AUTENTICACION_FALLIDA("security-0001"),
	KEY_CREATE_USER("security-0002");

	public final String value;
	private MensajesNegocioClaves(String value) {
		this.value = value;
	}
}

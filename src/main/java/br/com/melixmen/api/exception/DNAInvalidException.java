package br.com.melixmen.api.exception;

public class DNAInvalidException extends RuntimeException {

	private static final long serialVersionUID = 2019010901L;

	public DNAInvalidException(String message) {
		super(message);
	}
}


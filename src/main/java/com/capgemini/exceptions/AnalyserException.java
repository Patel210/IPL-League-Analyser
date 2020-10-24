package com.capgemini.exceptions;

public class AnalyserException extends Exception {
	
	public ExceptionType type;

	public enum ExceptionType {
		NO_DATA_FOUND;
	}

	public AnalyserException(String message, ExceptionType exceptionType) {
		super(message);
		this.type = exceptionType;
	}
}

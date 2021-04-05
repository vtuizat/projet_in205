package com.ensta.librarymanager.service;

public class ServiceException extends Exception{
    private static final long serialVersionUID = 1L;


	public ServiceException()
	{
		super();
	}
	
	public ServiceException(String message)
	{
		super(message);
	}
	
	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

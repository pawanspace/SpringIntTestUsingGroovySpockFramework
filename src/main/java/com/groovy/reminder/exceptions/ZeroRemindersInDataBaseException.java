package com.groovy.reminder.exceptions;

public class ZeroRemindersInDataBaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getLocalizedMessage() {
		
		return "Nothing exist in database for Reminder.";

	}
	
}

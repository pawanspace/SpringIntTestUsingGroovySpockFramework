package com.groovy.reminder.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Reminder {

	@Id
	private Integer id;
	@Size(min = 10)
	private String text;

	

	public Reminder() {
	}
	
	
	public Reminder(String text, Integer id) {
		this.text = text;
		this.id = id;
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
}

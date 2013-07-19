package com.groovy.reminder.service;

import java.io.IOException;
import java.util.List;

import com.groovy.reminder.model.Reminder;


public interface ReminderService {
	public void setReminder(Reminder reminder) throws IOException ;
	public List<Reminder> getReminders();
	public Reminder getReminder(Integer id);
}

package com.groovy.reminder.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groovy.reminder.exceptions.ZeroRemindersInDataBaseException;
import com.groovy.reminder.model.Reminder;
import com.groovy.reminder.service.ReminderService;


@Service
@Transactional
public class CoolReminderService implements ReminderService {
	
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public CoolReminderService(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	@Override
	public void setReminder(Reminder reminder) throws IOException {
		File file = new File("notes.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.write("Creating reminder with id: "+reminder.getId());
		writer.newLine();
		writer.close();
		hibernateTemplate.save(reminder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reminder> getReminders() {
		List<Reminder> reminders = hibernateTemplate.find("from Reminder");
		assertForEmpty(reminders);
		return reminders;
	}

	@Override
	public Reminder getReminder(Integer reminderId) {
		return hibernateTemplate.get(Reminder.class, reminderId);
	}

	private void assertForEmpty(Collection<? extends Object> collection) {
		if(collection == null || collection.size() == 0){
			throw new ZeroRemindersInDataBaseException();
		}
	}
}
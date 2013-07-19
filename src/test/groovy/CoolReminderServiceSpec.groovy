

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groovy.reminder.service.ReminderService;
import com.groovy.reminder.model.Reminder;
import com.groovy.reminder.exceptions.ZeroRemindersInDataBaseException;

import spock.lang.*


@ContextConfiguration("classpath*:spring-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(propagation=Propagation.REQUIRES_NEW)
class CoolReminderServiceSpec extends spock.lang.Specification {
    
	@Autowired
	ReminderService coolReminderService;
	
	@Shared File file;
	@Shared BufferedReader reader;
	
	def setupSpec(){
		file = new File("notes.txt");
		file.createNewFile();
		reader = new BufferedReader(new FileReader(file));
	}
	
	
	def cleanupSpec(){
		reader.close();
		boolean deleted = file.delete();
		assert deleted
	}
	
	def "should be able to create reminder in database"() {
		
		when:"I insert reminder in database"
		createReminders("Prepare for Demo on friday",1)
		
		then:
		this.coolReminderService.getReminders().size() == 1;
		reader.readLine().equals("Creating reminder with id: 1")
    }
	
	def "should be able to create multiple reminders in database"() {
		
		when:
		createReminders("Fill Timesheet on friday",1)
		createReminders("Talk to Melissa about new requirements",2)
		
		
		then:
		def reminders = this.coolReminderService.getReminders();
		reminders.size() == 2;
		reminders.get(0).getText().equals("Fill Timesheet on friday")
	}
	
	def "should be able to fetch particular reminder in database"() {
		
		given:
		createReminders("Call Jim Olsen",1)
		createReminders("Check if there is something better than Spock",2)
		
		expect:
		this.coolReminderService.getReminder(reminderId).getText().equals(reminderText)
		
		where:
		reminderId 	 << [1,2]
		reminderText << ["Call Jim Olsen", "Check if there is something better than Spock"]
	}
	
	def "should return null when reminder does not exist in database"() {
		
		when: "There is nothing in the database"
		
		then:
		this.coolReminderService.getReminder(reminderId) == null
		
		where:
		reminderId << [1,2]
		
	}

	def "should throw exception when reminder does not exist in database"() {
		
		when: "There is nothing in the database"
		this.coolReminderService.getReminders()
		
		then:
		ZeroRemindersInDataBaseException ex = thrown()
		ex.getLocalizedMessage().equals("Nothing exist in database for Reminder.")
		
	}
	
	def createReminders(text, id){
		this.coolReminderService.setReminder(new Reminder(text, id))
	}
}
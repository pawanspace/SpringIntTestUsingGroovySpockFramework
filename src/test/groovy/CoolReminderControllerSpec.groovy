

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.spockframework.springintegration.web.WebContextConfiguration;
import org.spockframework.springintegration.web.WebSpecification;

import spock.lang.Shared;

import com.groovy.reminder.model.Reminder;

@Transactional(propagation=Propagation.REQUIRES_NEW)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@WebContextConfiguration(value="WEB-INF/dispatcher-servlet.xml" , contextConfiguration = @ContextConfiguration("classpath*:spring-context.xml"))
class CoolReminderControllerSpec extends WebSpecification{
    
	@Autowired
	HibernateTemplate hibernateTemplate;
	

	def "Should validate user input and return error if it's not value"(){
		
		when:
		def response = post("/createReminder.post", [id:"1" , text:""]);
	
		
		then:
		response.hasFieldErrorFor("text")
	}
	
	def "Should be able to insert Reminder in database"(){
		
		when:
		def response = post("/createReminder.post", [id:"1" , text:"Go for StarBucks with team."]);
		
		then:
		response.hasFieldErrorFor("text") == false
		Reminder reminder = hibernateTemplate.load(Reminder.class, 1);
		reminder.getText().equals("Go for StarBucks with team.")
	}
	
	def "Should be able to fetch Reminder from database"(){
		
		given:
		post("/createReminder.post", [id:"2" , text:"Pay internet bill by end of the day."])
		
		when:
		def webObject = get("/getReminder/%s.get",2);
		Reminder reminder = webObject.getModelAttribute("reminder");
		
		then:
		webObject.hasFieldErrorFor("text") == false
		reminder.getText().equals("Pay internet bill by end of the day.")
	}
	
	
	def "Should contain Exception details when some error occured"(){
		
		when:
		def webObject = get("/getReminder/%s.get",null);
		
		then:
		webObject.getAttribute("exception") != null
		((Exception)(webObject.getAttribute("exception"))).getClass().equals(TypeMismatchException.class)
	}
	
	

	
}
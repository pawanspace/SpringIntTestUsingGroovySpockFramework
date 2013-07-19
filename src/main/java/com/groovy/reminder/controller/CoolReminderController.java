package com.groovy.reminder.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.groovy.reminder.model.Reminder;
import com.groovy.reminder.service.ReminderService;

@Controller
public class CoolReminderController {

	
	@Autowired
	private ReminderService reminderService;
	

	@RequestMapping(value="/createReminder.post",method = RequestMethod.POST)
	public String createControler(@Valid @ModelAttribute Reminder reminder,  BindingResult results, ModelMap map) throws IOException{
		if(results.hasFieldErrors()){
			return "error";
		}
		System.out.println(reminder.getText());
		reminderService.setReminder(reminder);
		return "Success";
	}
	
	
	@RequestMapping(value="/getReminder/{reminderId}.get")
	public String getReminder(@PathVariable int reminderId , Model model, HttpServletRequest request) throws IOException{
		model.addAttribute("reminder", reminderService.getReminder(reminderId));
		return "ShowUser";
	}
	

	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex){
		System.out.println("###############################Exception occured###########################");
		ModelAndView  modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", ex);
		return modelAndView;
	}
	
	
}

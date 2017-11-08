package com.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.model.User;
import com.demo.service.UserService;


/**
 * @author ankidaemon
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView renderHome() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}

	@RequestMapping(value = {"/create","/"},method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			System.out.println("result has errors"); 
			attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);
			return "redirect:/";
		}
		service.createUser(user);
		attr.addFlashAttribute("added","User with username : "+user.getUserName()+" added successfully.");
		return "redirect:/";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attr) {
		ModelAndView mav = new ModelAndView("home");
		service.update(user);
		mav.addObject("updated","User having id: "+user.getUserId()+" successfully updated.");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView findOne(@PathVariable("id") int userId) {
		ModelAndView mav = new ModelAndView("home");
		List<User> users=new ArrayList<>();
		users.add(service.findOne(userId));
		mav.addObject("userList",users);
		return mav;
	}
	
	@RequestMapping(value = "/all",method = RequestMethod.GET)
	public ModelAndView findAll() {
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("userList",service.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView("home");
		service.delete(id);
		mav.addObject("deleted","User having id: "+id+" successfully deleted.");
		return mav;
	}

}

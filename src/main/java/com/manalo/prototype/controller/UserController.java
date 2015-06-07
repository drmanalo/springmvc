package com.manalo.prototype.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.manalo.prototype.constant.ControllerConstant;
import com.manalo.prototype.form.UserForm;
import com.manalo.prototype.service.UserService;

@Controller
public class UserController {

	@Inject
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return ControllerConstant.USER_LOGIN;
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout() {
		return ControllerConstant.USER_LOGOUT;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "listUsers", method = RequestMethod.GET)
	public ModelAndView listUsers() {

		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userService.list());
		mav.setViewName(ControllerConstant.USER_LIST);

		return mav;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public ModelAndView addUser() {

		UserForm userForm = new UserForm();
		userForm.setAction(ControllerConstant.CREATE_ACTION);

		return setMav(userForm);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "editUser", method = RequestMethod.GET)
	public ModelAndView editUser(Integer id) {

		UserForm userForm = userService.loadUser(id);
		userForm.setAction(ControllerConstant.UPDATE_ACTION);

		return setMav(userForm);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("form") UserForm userForm, RedirectAttributes flash) {

		if (userForm.getAction().equals(ControllerConstant.CREATE_ACTION)) {
			userService.save(userForm);
			flash.addFlashAttribute("user_added", userForm.getUsername());
		}

		if (userForm.getAction().equals(ControllerConstant.UPDATE_ACTION)) {
			userService.update(userForm);
			flash.addFlashAttribute("user_updated", userForm.getUsername());
		}

		return "redirect:listUsers";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "deleteUser", method = RequestMethod.GET)
	public String deleteUser(Integer id, RedirectAttributes flash) {
		String username = userService.delete(id);
		flash.addFlashAttribute("user_deleted", username);
		return "redirect:listUsers";
	}

	@ModelAttribute("roleMap")
	public Map<String, String> roleMap() {

		Map<String, String> roles = new HashMap<>();
		roles.put("ROLE_ADMIN", "Administrator");
		roles.put("ROLE_USER", "Web User");

		return roles;
	}

	private ModelAndView setMav(UserForm userForm) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("form", userForm);
		mav.setViewName(ControllerConstant.CREATE_UPDATE_USER);

		return mav;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

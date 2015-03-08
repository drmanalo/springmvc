package com.manalo.prototype.controller;

import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.manalo.prototype.constant.ControllerConstant;
import com.manalo.prototype.entity.User;
import com.manalo.prototype.form.UserForm;
import com.manalo.prototype.service.UserService;

public class UserControllerTest {
	
	private UserService userService;
	private UserController controller;
	
	private Errors errors;
	private Model model;
	private RedirectAttributes flash;
	
	private List<User> users = new ArrayList<>();
	
	@Test
	public void login() {
		String actual = controller.login();
		assertThat(actual).isEqualTo(ControllerConstant.USER_LOGIN);
	}
	
	@Test
	public void listUsers() {
		
		ModelAndView actual = controller.listUsers();
		
		assertThat(actual.getViewName()).isEqualTo(ControllerConstant.USER_LIST);
		assertThat(actual.getModelMap().get("users")).isEqualTo(users);
	}
	
	@Test
	public void addUser() {
		
		ModelAndView actual = controller.addUser();
		UserForm userForm = (UserForm) actual.getModelMap().get("form");
		
		assertThat(actual.getViewName()).isEqualTo(ControllerConstant.CREATE_UPDATE_USER);
		assertThat(userForm.getAction()).isEqualTo(ControllerConstant.CREATE_ACTION);
	}
	
	@Test
	public void editUser() {
		
		Integer id = 345;
		UserForm form = new UserForm();
		when(userService.loadUser(id)).thenReturn(form);
		
		ModelAndView actual = controller.editUser(id);
		UserForm userForm = (UserForm) actual.getModelMap().get("form");
		
		assertThat(actual.getViewName()).isEqualTo(ControllerConstant.CREATE_UPDATE_USER);
		assertThat(userForm.getAction()).isEqualTo(ControllerConstant.UPDATE_ACTION);
	}
	
	@Test
	public void saveUser_WithError() {
		
		UserForm userForm = new UserForm();
		when(errors.hasErrors()).thenReturn(true);
		
		String actual = controller.saveUser(userForm, errors, model, flash);
		
		assertThat(actual).isEqualTo(ControllerConstant.CREATE_UPDATE_USER);
		verify(model).addAttribute("form", userForm);
	}
	
	@Test
	public void saveUser_CreateAction() {
		
		String username = "tester";
		
		UserForm userForm = new UserForm();
		userForm.setUsername(username);
		userForm.setAction(ControllerConstant.CREATE_ACTION);
		
		when(errors.hasErrors()).thenReturn(false);
		
		String actual = controller.saveUser(userForm, errors, model, flash);
		
		assertThat(actual).isEqualTo("redirect:listUsers");
		verify(userService).save(userForm);
		verify(flash).addFlashAttribute("user_added", username);
	}
	
	@Test
	public void saveUser_UpdateAction() {
		
		String username = "tester";
		
		UserForm userForm = new UserForm();
		userForm.setUsername(username);
		userForm.setAction(ControllerConstant.UPDATE_ACTION);
		
		when(errors.hasErrors()).thenReturn(false);
		
		String actual = controller.saveUser(userForm, errors, model, flash);
		
		assertThat(actual).isEqualTo("redirect:listUsers");
		verify(userService).update(userForm);
		verify(flash).addFlashAttribute("user_updated", username);
	}
	
	@Test
	public void deleteUser() {
		
		Integer id = 234;
		String username = "tester";
		when(userService.delete(id)).thenReturn(username);
		
		controller.deleteUser(id, flash);
		verify(flash).addFlashAttribute("user_deleted", username);
	}
	
	@Test
	public void roleMap() {
		Map<String, String> actual = controller.roleMap();
		assertThat(actual.get("ROLE_ADMIN")).isEqualTo("Administrator");
		assertThat(actual.get("ROLE_USER")).isEqualTo("Web User");
	}
	
	@BeforeClass
	public void setup() {
		
		userService = mock(UserService.class);
		when(userService.list()).thenReturn(users);
		
		controller = new UserController();
		controller.setUserService(userService);
		
		errors = mock(Errors.class);
		model = mock(Model.class);
		flash = mock(RedirectAttributes.class);
	}
	
}

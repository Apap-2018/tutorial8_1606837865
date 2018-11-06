package com.apap.tutorial6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.model.PasswordModel;
import com.apap.tutorial6.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser" , method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping("/password")
	public String updatePassword() {
		return "password";
	}
	
	@RequestMapping(value = "/password" , method = RequestMethod.POST)
	public String updatePasswordSubmit(@ModelAttribute PasswordModel password, Model model) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String msg = "";
		if(password.getPasswordKonfirm().equals(password.getPasswordBaru())) {
			
			if(passwordEncoder.matches(password.getPasswordLama(), user.getPassword())) {
				userService.ubahPassword(user, password.getPasswordBaru());
				msg = "Password Berhasil Diubah";
			}else {
				msg = "Password Lama Salah";
			}
			
		}else {
			msg = "Password Tidak Sama";
		}
		
		
		return "password-success";
	}
}

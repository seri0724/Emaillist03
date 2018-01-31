package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.EmaillistDao;
import com.javaex.vo.EmaillistVo;

@Controller
public class EmaillistController {
	
	@Autowired
	private EmaillistDao emaillistDao;
	
	//localhost:8088/emaillist3/form
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form() {
		System.out.println("form");
		return"form";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute EmaillistVo emaillistVo) {
		System.out.println(emaillistVo.toString());
		emaillistDao.insert(emaillistVo);
		return"redirect:/list";
	}
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		List<EmaillistVo> eList = emaillistDao.getList();
		System.out.println(eList.toString());
		model.addAttribute("eList", eList);
		return"list";
	}
}
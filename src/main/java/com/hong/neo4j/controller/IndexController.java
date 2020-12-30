package com.hong.neo4j.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @Author : KongJHong
 * @Date : 2020-12-15 9:32
 * @Version : 1.0
 * Description     :
 */
@Controller
public class IndexController {


	public IndexController() {
	}

	@GetMapping("/graph")
	public String index() {
		return "graph";
	}

	@GetMapping({"/home", "/", "index"})
	public String home() {
		return "home";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/detail")
	public String detail(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) model.addAttribute("id", id);
		return "detail";
	}

	@GetMapping("/detail-edit")
	public String edit(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) model.addAttribute("id", id);
		return "detail-edit";
	}
}

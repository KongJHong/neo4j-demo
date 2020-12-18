package com.hong.neo4j.controller;

import com.hong.neo4j.service.RelationGraphXService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : KongJHong
 * @Date : 2020-12-15 9:32
 * @Version : 1.0
 * Description     :
 */
@Controller
public class IndexController {

	private final RelationGraphXService relationGraphXService;

	public IndexController(RelationGraphXService relationGraphXService) {
		this.relationGraphXService = relationGraphXService;
	}

	@GetMapping("/home")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("title", "自定义题目");

		return "index";
	}

}

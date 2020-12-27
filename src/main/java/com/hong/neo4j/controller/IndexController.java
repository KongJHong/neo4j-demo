package com.hong.neo4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String detail(){
		return "detail";
	}

}

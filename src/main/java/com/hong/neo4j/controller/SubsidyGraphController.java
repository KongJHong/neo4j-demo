package com.hong.neo4j.controller;

import com.hong.neo4j.dto.SubsidyDTO;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import com.hong.neo4j.dto.SubsidyNodeDTO;
import com.hong.neo4j.service.SubsidyGraphService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-21 17:02
 * @Version : 1.0
 * Description     : 补贴API
 */
@RestController
@RequestMapping("/graph")
public class SubsidyGraphController {

	private final SubsidyGraphService subsidyGraphService;

	public SubsidyGraphController(SubsidyGraphService subsidyGraphService){
		this.subsidyGraphService = subsidyGraphService;

	}

	@GetMapping("/test")
	public void addGraphNode() {
		subsidyGraphService.addGraphNode();
	}


	@GetMapping("/data/{id}")
	public SubsidyDTO findById(@PathVariable String id) {
		return subsidyGraphService.findById(id);
	}

	@GetMapping("/policy/{title}")
	public List<SubsidyNodeDTO> findAllPolicyNodeByTitle(@PathVariable String title) {
		return subsidyGraphService.findAllPolicyNodeByTitle(title);
	}

	@GetMapping("/all")
	public List<SubsidyGraphDTO> findAllGraph() {
		return subsidyGraphService.findAllGraph();
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable String id) {
		return subsidyGraphService.deleteById(id);
	}
}

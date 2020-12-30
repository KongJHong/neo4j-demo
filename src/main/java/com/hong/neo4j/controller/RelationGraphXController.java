package com.hong.neo4j.controller;

import com.hong.neo4j.domain.GraphNode;
import com.hong.neo4j.enums.NodeTypeEnum;
import com.hong.neo4j.service.RelationGraphXService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-14 9:31
 * @Version : 1.0
 * Description     :
 */
@Deprecated
@RestController
@RequestMapping("/relation/graph")
public class RelationGraphXController {

	private final RelationGraphXService relationGraphXService;

	public RelationGraphXController(RelationGraphXService relationGraphXService) {
		this.relationGraphXService = relationGraphXService;
	}

	@GetMapping("/test")
	public List<GraphNode> test() {
		return relationGraphXService.findAllNodes();
	}


	@GetMapping("/addTest")
	public List<GraphNode> addTest() {
		return this.relationGraphXService.addTest();
	}

	@GetMapping("/addTest2")
	public List<GraphNode> addNode() {
		return relationGraphXService.addTest2();
	}


	@GetMapping("/name/{name}")
	public List<GraphNode> findByName(@PathVariable String name) {
		return relationGraphXService.findAllPersonByName(name);
	}



	@GetMapping("/out/{title}")
	public List<GraphNode> findAllOutNode(@PathVariable String title) {
		return relationGraphXService.findAllOutNodeByTitle(title);
	}

	@GetMapping("/out/{title}/{type}")
	public List<GraphNode> findAllOutNodeByTitleAndType(@PathVariable String title,@PathVariable NodeTypeEnum type) {
		return relationGraphXService.findAllOutNodeByTitleAndType(title, type);
	}
}

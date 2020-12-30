package com.hong.neo4j.controller;

import com.hong.neo4j.dto.SubsidyDTO;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import com.hong.neo4j.dto.SubsidyNodeDTO;
import com.hong.neo4j.dto.SubsidyRelationDTO;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import com.hong.neo4j.service.SubsidyGraphService;
import org.apache.ibatis.annotations.Delete;
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

	/**
	 * 添加测试用例
	 */
	@GetMapping("/test")
	public void addGraphNode() {
		subsidyGraphService.addGraphNode();
	}

	/**
	 * 根据id查找政策图谱
	 */
	@GetMapping("/data/{id}")
	public SubsidyDTO findById(@PathVariable String id) {
		return subsidyGraphService.findById(id);
	}

	/**
	 * 查找单一节点的某种关系，这里默认了政策间的关系
	 */
	@GetMapping("/policy/{coreNode}")
	public List<SubsidyNodeDTO> findAllPolicyNodeByTitle(@PathVariable String coreNode) {
		return subsidyGraphService.findAllPolicyNodeByTitle(coreNode);
	}

	/**
	 * 查找所有图谱
	 */
	@GetMapping("/all")
	public List<SubsidyGraphDTO> findAllGraph() {
		return subsidyGraphService.findAllGraph();
	}

	/**
	 * 搜索栏用，根据关键字搜索图谱
	 */
	@GetMapping("/all/{keyword}")
	public List<SubsidyGraphDTO> findAllGraphByKeyword(@PathVariable String keyword) {
		return subsidyGraphService.findAllGraphByTitleOrContent(keyword);
	}

	/**
	 * 根据id删除图谱
	 */
	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable String id) {
		return subsidyGraphService.deleteById(id);
	}

	/**
	 * 保存/更新图谱的内容，包括图谱本身的内容，节点信息和关系
	 */
	@PostMapping("/")
	public SubsidyDTO saveOrUpdate(@RequestBody SubsidyDTO subsidyDTO) {
		System.out.println(subsidyDTO);
		return subsidyGraphService.saveOrUpdate(subsidyDTO);
	}

	/**
	 * 根据连线的id删除关系
	 */
	@DeleteMapping("/link/{id}")
	public boolean deleteLinkById(@PathVariable Long id) {
		return subsidyGraphService.deleteLinkById(id);
	}

	/**
	 * 根据节点id删除节点
	 */
	@DeleteMapping("/node/{id}")
	public boolean deleteNodeById(@PathVariable Long id) {
		return subsidyGraphService.deleteNodeById(id);
	}

	@GetMapping("/node/policy")
	public List<SubsidyNodeDTO> findAllPolicyNode() {
		return subsidyGraphService.findAllPolicyNode();
	}

	@GetMapping("/relation/{coreNode}")
	public List<SubsidyRelationDTO> findAllPointingRelationByStartCoreNode(@PathVariable String coreNode) {
		return subsidyGraphService.findAllRelationByStartCoreNodeAndEndType(coreNode, SubsidyTypeEnum.NORMAL);
	}

	@DeleteMapping("/relation/{id}")
	public boolean deleteRelationById(@PathVariable Long id) {
		return subsidyGraphService.deleteRelationById(id);
	}
}

package com.hong.neo4j.service;

import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.dto.SubsidyDTO;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import com.hong.neo4j.dto.SubsidyNodeDTO;

import java.util.List;
import java.util.Map;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 11:39
 * @Version : 1.0
 * Description     :
 */
public interface SubsidyGraphService {

	/**
	 * 添加测试节点
	 */
	void addGraphNode();

	SubsidyDTO findById(String id);

	List<SubsidyNodeDTO> findAllPolicyNodeByTitle(String title);

	List<SubsidyGraphDTO> findAllGraph();

	/**
	 * 删除图谱
	 * @param id 图谱id
	 * @return true/false
	 */
	boolean deleteById(String id);

	Integer insertBatchGraph(List<SubsidyGraph> graphs);

	Integer insertBatchNode(List<SubsidyNode> nodes);

	Map<String, String> findAllGraphsIdAndName();
}

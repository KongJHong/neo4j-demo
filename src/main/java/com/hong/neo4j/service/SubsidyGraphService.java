package com.hong.neo4j.service;

import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.dto.SubsidyDTO;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import com.hong.neo4j.dto.SubsidyNodeDTO;
import com.hong.neo4j.dto.SubsidyRelationDTO;
import com.hong.neo4j.enums.SubsidyTypeEnum;

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

	SubsidyDTO saveOrUpdate(SubsidyDTO subsidyDTO);

	/**
	 * 查找单一节点的关系，这里默认了政策之间的关系
	 * @param coreNode 核心节点的名称
	 * @return 节点列表
	 */
	List<SubsidyNodeDTO> findAllPolicyNodeByTitle(String coreNode);

	List<SubsidyNodeDTO> findAllPolicyNode();

	List<SubsidyGraphDTO> findAllGraph();

	List<SubsidyGraphDTO> findAllGraphByTitleOrContent(String keyword);

	/**
	 * 删除图谱
	 * @param id 图谱id
	 * @return true/false
	 */
	boolean deleteById(String id);

	Integer insertBatchGraph(List<SubsidyGraph> graphs);

	Integer insertBatchNode(List<SubsidyNode> nodes);

	Map<String, String> findAllGraphsIdAndName();

	boolean deleteLinkById(Long id);

	boolean deleteNodeById(Long id);

	/**
	 * 根据开始节点的名称和结束节点的类型查找关系
	 * @param coreNode	开始节点的名称
	 * @param type 结束节点的类型
	 * @return 关系数组
	 */
	List<SubsidyRelationDTO> findAllRelationByStartCoreNodeAndEndType(String coreNode, SubsidyTypeEnum type);

	boolean deleteRelationById(Long id);


}

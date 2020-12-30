package com.hong.neo4j.service;


import com.hong.neo4j.domain.GraphNode;
import com.hong.neo4j.enums.NodeTypeEnum;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-14 9:34
 * @Version : 1.0
 * Description     :
 */
@Deprecated
public interface RelationGraphXService {

	List<GraphNode> addTest();

	/**
	 * 添加测试节点
	 */
	List<GraphNode> addTest2();


	List<GraphNode> findAllNodes();


	/**
	 * 根据名称查找所有入度和出度对象
	 * @param title 名称
	 * @return 用户对象，其中包含了所有的入度和出度节点
	 */
	List<GraphNode> findAllPersonByName(String title);


	/**
	 * 寻找该节点的所有指向节点
	 * @param title 节点title
	 * @return node数组
	 */
	List<GraphNode> findAllOutNodeByTitle(String title);

	/**
	 * 寻找该节点的名称和指向类型找到所有指向节点
	 * @param title 名称
	 * @param type  指向类型
	 * @return node数组
	 */
	List<GraphNode> findAllOutNodeByTitleAndType(String title, NodeTypeEnum type);
}

package com.hong.neo4j.repository;

import com.hong.neo4j.domain.GraphNode;
import com.hong.neo4j.enums.NodeTypeEnum;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-16 17:12
 * @Version : 1.0
 * Description     :
 */
@Repository
@Deprecated
public interface GraphNodeRepository extends Neo4jRepository<GraphNode, Long> {

	/**
	 * 根据title查找所有的出度节点
	 * @param title 名称
	 * @return node列表 包括本节点
	 */
	@Query("MATCH (n:Node), (m:Node{title:$title}) WHERE (n)-[:relation]->(m) OR (m)-[:relation]->(n) return n,m")
	List<GraphNode> findAllInOutNodeByTitle(String title);

	/**
	 * 根据title查找所有的出度节点
	 * @param title 名称
	 * @return node列表 包括本节点
	 */
	@Query("MATCH (n:Node), (m:Node{title:$title}) WHERE (m)-[:relation]->(n) return n,m")
	List<GraphNode> findAllOutNodeByTitle(String title);

	/**
	 * 根据title和type找出该节点的出度节点
	 * @param title 名称
	 * @param type	指向类型
	 * @return node列表 包含本节点
	 */
	@Query("MATCH (n:Node{type:$type}), (m:Node{title:$title}) WHERE (m)-[:relation]->(n) return n,m")
	List<GraphNode> findAllOutNodeByTitleAndType(String title, NodeTypeEnum type);
}

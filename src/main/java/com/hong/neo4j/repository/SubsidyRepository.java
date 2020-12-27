package com.hong.neo4j.repository;

import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : KongJHong
 * @Date : 2020-12-21 17:00
 * @Version : 1.0
 * Description     : 补贴搜索
 */
@Repository
public interface SubsidyRepository extends Neo4jRepository<SubsidyNode, Long> {

	List<SubsidyNode> findAllByGraphId(String graphId);


	List<Long> deleteAllByGraphId(String graphId);

	/**
	 * 根据类型找到所有和${title}节点有关的所有节点
	 *
	 * @param type  要找的节点的类型
	 * @param title 根据这个title找到目标节点
	 * @return 节点数组
	 */
	@Query("MATCH (n:Subsidy{title:$title}) MATCH (m:Subsidy{type:$type}) WHERE (n)-[:relation]->(m) OR (m)-[:relation]->(n) return n,m")
	List<SubsidyNode> findAllByTypeAndTitle(SubsidyTypeEnum type, String title);

	/**
	 * 排除某种类型，根据核心的节点名称找到和它有关的所有节点
	 * Neo4j中 NOT IN是无效的 只能是 NOT (aa in bb)
	 * @param graphId 隶属的图谱id
	 * @param title   核心节点的名称
	 * @param type    要排除的类型
	 * @return 节点数组
	 */
	@Query("MATCH (n:Subsidy) MATCH(m:Subsidy{title:$title}) WHERE n.graphId = $graphId AND NOT (n.type IN $type) RETURN n,m")
	List<SubsidyNode> findAllByGraphIdAndTitleAndNotEqualWithType(String graphId, String title, SubsidyTypeEnum ... type);
}

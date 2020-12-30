package com.hong.neo4j.repository;

import com.hong.neo4j.domain.SubsidyRelation;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-27 18:52
 * @Version : 1.0
 * Description     :
 */
@Repository
public interface SubsidyRelationRepository extends Neo4jRepository<SubsidyRelation, Long> {

	List<SubsidyRelation> findAllByGraphId(String graphId);

	@Query("MATCH p=(:Subsidy{title:$coreNode})-[r:node_relation]->(:Subsidy{type:$type}) return p")
	List<SubsidyRelation> findAllByStartNodeTitleAndEndNodeType(String coreNode, SubsidyTypeEnum type);
}

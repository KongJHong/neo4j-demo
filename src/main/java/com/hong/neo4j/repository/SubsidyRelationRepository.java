package com.hong.neo4j.repository;

import com.hong.neo4j.domain.SubsidyRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : KongJHong
 * @Date : 2020-12-27 18:52
 * @Version : 1.0
 * Description     :
 */
@Repository
public interface SubsidyRelationRepository extends Neo4jRepository<SubsidyRelation, Long> {
}

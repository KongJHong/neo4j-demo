package com.hong.neo4j.mapper;

import com.hong.neo4j.domain.SubsidyGraph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 11:38
 * @Version : 1.0
 * Description     :
 */
@Mapper
public interface SubsidyGraphMapper extends MyBaseMapper<SubsidyGraph> {

	@Select("select id, name from subsidy_graph where deleted = 0")
	List<SubsidyGraph> findAllGraphsIdAndName();
}

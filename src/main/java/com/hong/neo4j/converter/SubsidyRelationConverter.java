package com.hong.neo4j.converter;

import com.hong.neo4j.domain.SubsidyRelation;
import com.hong.neo4j.dto.SubsidyRelationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-27 21:51
 * @Version : 1.0
 * Description     :
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SubsidyRelationConverter {

	@Mappings({
			@Mapping(source = "startNode.title", target = "startNodeName"),
			@Mapping(source = "endNode.title", target = "endNodeName")
	})
	SubsidyRelationDTO toDTO(SubsidyRelation entity);

	List<SubsidyRelationDTO> toDTOList(List<SubsidyRelation> entities);
}

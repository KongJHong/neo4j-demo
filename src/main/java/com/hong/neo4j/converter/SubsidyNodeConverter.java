package com.hong.neo4j.converter;

import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.dto.SubsidyNodeDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 17:03
 * @Version : 1.0
 * Description     :
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SubsidyNodeConverter {

	SubsidyNodeDTO toDTO(SubsidyNode entity);

	List<SubsidyNodeDTO> toDTOList(List<SubsidyNode> entities);

	SubsidyNode toEntity(SubsidyNodeDTO dto);

	List<SubsidyNode> toEntities(List<SubsidyNodeDTO> dtos);
}

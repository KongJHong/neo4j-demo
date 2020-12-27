package com.hong.neo4j.converter;

import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 17:02
 * @Version : 1.0
 * Description     :
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SubsidyGraphConverter {

	@Mapping(source = "keywords", target = "keywords", qualifiedByName = "string2Set")
	SubsidyGraphDTO toDTO(SubsidyGraph entity);

	List<SubsidyGraphDTO> toDTOList(List<SubsidyGraph> entities);

	@Named("string2Set")
	default Set<String> string2Set(String keywords) {
		Set<String> set = new HashSet<>();
		if (StringUtils.isEmpty(keywords))return set;

		String[] keyword = keywords.split(",");
		set.addAll(Arrays.asList(keyword));
		return set;
	}

}

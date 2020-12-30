package com.hong.neo4j.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 16:52
 * @Version : 1.0
 * Description     : 补贴图谱dto
 */
@Data
public class SubsidyDTO {

	SubsidyGraphDTO graphDTO;

	List<SubsidyNodeDTO> subsidyNodeDTOS;

	List<SubsidyRelationDTO> subsidyRelationDTOS;

	List<SubsidyRelationDTO> outerRelationDTOS;

}

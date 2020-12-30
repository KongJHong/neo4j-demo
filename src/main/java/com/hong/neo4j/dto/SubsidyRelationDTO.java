package com.hong.neo4j.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author : KongJHong
 * @Date : 2020-12-27 21:50
 * @Version : 1.0
 * Description     :
 */
@Data
public class SubsidyRelationDTO {

	private Long id;

	private String startNodeName;

	private String endNodeName;

	private Date create;

	private String graphId;

	private String comment;
}

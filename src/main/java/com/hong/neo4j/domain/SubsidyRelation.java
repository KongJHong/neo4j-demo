package com.hong.neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author : KongJHong
 * @Date : 2020-12-27 18:46
 * @Version : 1.0
 * Description     : 节点关系
 */
@RelationshipEntity("node-relation")
@Data
public class SubsidyRelation {

	@Id
	@GeneratedValue
	private Long id;

	@StartNode
	private SubsidyNode startNode;

	@EndNode
	private SubsidyNode endNode;

	/**
	 * 关系描述
	 */
	private String comment;

	@DateLong
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date create;

	public SubsidyRelation(SubsidyNode startNode, SubsidyNode endNode, String comment, Date create) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.comment = comment;
		this.create = create;
	}

}

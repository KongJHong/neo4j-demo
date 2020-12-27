package com.hong.neo4j.dto;

import com.hong.neo4j.enums.SubsidyTypeEnum;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @Author : KongJHong
 * @Date : 2020-12-22 16:53
 * @Version : 1.0
 * Description     : 补贴的节点DTO
 */
@Data
public class SubsidyNodeDTO {

	private Long id;

	/**
	 * 补贴类的id
	 */
	private String graphId;

	/**
	 * 关系权重
	 */
	private Integer weight;


	private String title;


	private Date date;

	/**
	 * 关联类型,以后用枚举
	 * 用于在关系连线上的字段显示
	 */
	private SubsidyTypeEnum type;


	/**
	 * 节点内容
	 */
	private String content;

	/**
	 * 标签
	 */
	private Set<String> tags = new HashSet<>();


	/**
	 * 出度数组, [本节点]指向[pointingNodes]
	 * 因为neo4j不能存自定义对象，所以，只能用字符串数组构建
	 * 格式: title\$type\$description
	 */
	private Set<String> pointingNodes = new HashSet<>();
}

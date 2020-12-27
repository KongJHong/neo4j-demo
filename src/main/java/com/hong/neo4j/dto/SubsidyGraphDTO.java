package com.hong.neo4j.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 16:52
 * @Version : 1.0
 * Description     : 补贴图谱的DTO
 */
@Data
public class SubsidyGraphDTO {

	String id;
	/**
	 * 核心的core node名称
	 */
	String coreNode;
	/**
	 * 图谱名称
	 */
	String name;
	/**
	 * 标题
	 */
	String title;
	/**
	 * 所属类别
	 */
	String category;
	/**
	 * 审核状态
	 */
	String state;
	/**
	 * 资源ID
	 */
	Long resourceNo;
	/**
	 * 创建人
	 */
	String createdBy;
	/**
	 * 最后修改人
	 */
	String lastUpdateBy;
	/**
	 * 指示
	 */
	Map<String,String> indicates = new HashMap<>();
	/**
	 * 关键字
	 */
	Set<String> keywords;
	/**
	 * 描述
	 */
	String description;
	/**
	 * 内容
	 */
	String content;
	/**
	 * 创建时间
	 */
	LocalDateTime createTime;
	/**
	 * 更新时间
	 */
    LocalDateTime updateTime;
}

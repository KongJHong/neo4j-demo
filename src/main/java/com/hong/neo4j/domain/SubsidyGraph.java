package com.hong.neo4j.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.hong.neo4j.utils.JSONUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 10:43
 * @Version : 1.0
 * Description     : 补贴 图谱关联类
 */
@Data
@TableName("subsidy_graph")
public class SubsidyGraph {

	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	String id;

	/**
	 * 核心的node name
	 */
	@ExcelProperty("核心节点")
	String coreNode;

	/**
	 * 图谱名称
	 */
	@ExcelProperty("名称")
	String name;

	/**
	 * 标题
	 */
	@ExcelProperty("标题")
	String title;

	/**
	 * 所属类别
	 */
	@ExcelProperty("类别")
	String category;

	/**
	 * 审核状态
	 */
	@ExcelProperty("审核状态")
	String state;

	/**
	 * 资源ID
	 */
	@ExcelProperty("资源ID")
	Long resourceNo;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	String createdBy;

	/**
	 * 关键字
	 */
	@ExcelProperty("关键字")
	String keywords;

	/**
	 * 最后修改人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	String lastUpdateBy;

	/**
	 * 类型
	 */
	@ExcelProperty("类型")
	String type;

	/**
	 * 文号
	 */
	@ExcelProperty("文号")
	String proof;

	/**
	 * 类型手段
	 */
	@ExcelProperty("类型手段")
	String means;

	/**
	 * 所在地
	 */
	@ExcelProperty("所在地")
	String location;

	/**
	 * 政策内容
	 */
	@ExcelProperty("政策内容")
	String contentType;

	/**
	 * 发布机构
	 */
	@ExcelProperty("发布机构")
	String organization;

	/**
	 * 行业
	 */
	@ExcelProperty("行业")
	String industry;

	/**
	 * 描述
	 */
	String description;

	/**
	 * 内容
	 */
	String content;

	/**
	 * 逻辑删除
	 */
	@TableLogic
	Integer deleted = 0;

	@TableField(fill = FieldFill.INSERT)
	LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;


}

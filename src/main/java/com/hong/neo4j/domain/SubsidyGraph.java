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
	 * 指标信息，不存表：
	 * 类型：政策， 文号：财农， 类型手段：财政补贴等
	 */
	@TableField(exist = false)
	Map<String,String> indicates = new HashMap<>();

	/**
	 * 指标信息,存表：
	 * 类型：政策， 文号：财农， 类型手段：财政补贴等
	 */
	@TableField("indicatesStr")
	@ExcelProperty("指标信息")
	String indicatesStr;

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

    @SuppressWarnings("unchecked")
	public void setIndicatesStr(String indicatesStr) {
		if (indicates.isEmpty()) {
			indicates = JSONUtils.string2Obj(indicatesStr, HashMap.class);
		}
		this.indicatesStr = indicatesStr;
	}
}

package com.hong.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hong.neo4j.enums.NodeTypeEnum;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author : KongJHong
 * @Date : 2020-12-21 16:41
 * @Version : 1.0
 * Description     : 补贴业务的节点
 */
@NodeEntity(value = "Subsidy")
@NoArgsConstructor
@Getter
@Setter
public class SubsidyNode {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 补贴类的id
	 */
	private String graphId;

	/**
	 * 关系权重
	 */
	private Integer weight;

//	@Index(unique = true)
	private String title;

	@DateLong
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	/**
	 * 关联类型,以后用枚举
	 * 用于在关系连线上的字段显示
	 */
	private SubsidyTypeEnum type;

	/**
	 * 线段上的显示,不入库
	 */
	@Transient
	private String lineContent;

	/**
	 * 节点内容
	 */
	private String content;

	/**
	 * 标签
	 */
	private Set<String> tags = new HashSet<>();

	/**
	 * 它的所有关联节点，因为是图，所以很容易循环，绝对不能传出去给json
	 */
	@Relationship(type = "relation")
	@JsonIgnore
	private Set<SubsidyNode> subsidyNodes = new HashSet<>();

	/**
	 * 出度数组, [本节点]指向[pointingNodes]
	 * 因为neo4j不能存自定义对象，所以，只能用字符串数组构建
	 * 格式: title\$type\$description
	 */
	private Set<String> pointingNodes = new HashSet<>();

	public void addPointing(SubsidyNode ...nodes) {
		pointingNodes.addAll(Stream.of(nodes).map(SubsidyNode::toSimplify).collect(Collectors.toList()));
		subsidyNodes.addAll(Arrays.asList(nodes));
	}

	/**
	 * 添加标签s
	 * @param tags 标签s
	 */
	public void addTags(String ...tags) {
		this.tags.addAll(Stream.of(tags).collect(Collectors.toList()));
	}

	public SubsidyNode(String title,String graphId, Integer weight, Date date, SubsidyTypeEnum typeEnum, String content, String[] tags) {
		this.title = title;
		this.graphId = graphId;
		this.weight = weight;
		this.date = date;
		this.type = typeEnum;
		this.content = content;
		addTags(tags);
	}

	private static String toSimplify(SubsidyNode node) {
		String lineContent = StringUtils.isBlank(node.getLineContent())?"":node.getLineContent();
		return node.getTitle() + "\\$" + node.getType() + "\\$" + lineContent;
	}

	@Override
	public String toString() {
		return "SubsidyNode{" +
				"id=" + id +
				", graphId='" + graphId + '\'' +
				", weight=" + weight +
				", title='" + title + '\'' +
				", date=" + date +
				", type=" + type +
				", lineContent='" + lineContent + '\'' +
				", content='" + content + '\'' +
				", tags=" + tags +
				'}';
	}
}

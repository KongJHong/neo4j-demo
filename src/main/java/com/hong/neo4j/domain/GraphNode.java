package com.hong.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hong.neo4j.enums.NodeTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author : KongJHong
 * @Date : 2020-12-16 16:57
 * @Version : 1.0
 * Description     : 节点
 */
@NodeEntity(value = "Node")
@NoArgsConstructor
@Getter
@Setter
@Deprecated // 暂时不看
public class GraphNode {

	@Id
	@GeneratedValue
	private Long id;

	@Index(unique = true)
	private String title;

	@DateLong
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	/**
	 * 关联类型,以后用枚举
	 * 用于在关系连线上的字段显示
	 */
	private NodeTypeEnum type;

	private String description;

	/**
	 * 它的所有关联节点，因为是图，所以很容易循环，绝对不能传出去给json
	 */
	@Relationship(type = "relation")
	@JsonIgnore
	private Set<GraphNode> graphNodes = new HashSet<>();

	/**
	 * 出度数组, [本节点]指向[pointingNodes]
	 * 因为neo4j不能存自定义对象，所以，只能用字符串数组构建
	 * 格式: title\$type\$description
	 */
	private Set<String> pointingNodes = new HashSet<>();

	public void addPointing(GraphNode ...nodes) {
		pointingNodes.addAll(Stream.of(nodes).map(GraphNode::toSimplify).collect(Collectors.toList()));
		graphNodes.addAll(Arrays.asList(nodes));
	}

	private static String toSimplify(GraphNode node) {
		String description = StringUtils.isBlank(node.getDescription())?"":node.getDescription();
		return node.getTitle() + "\\$" + node.getType() + "\\$" + description;
	}

	@Override
	public String toString() {
		return "GraphNode{" +
				"id=" + id +
				", title='" + title + '\'' +
				", type=" + type +
				'}';
	}
}

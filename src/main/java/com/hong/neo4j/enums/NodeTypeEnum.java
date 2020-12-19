package com.hong.neo4j.enums;

/**
 * @Author : KongJHong
 * @Date : 2020-12-16 17:15
 * @Version : 1.0
 * Description     : 节点类型
 */
public enum NodeTypeEnum {

	PERSON,
	MOVIE;


	public static void main(String[] args) {
		NodeTypeEnum value = NodeTypeEnum.valueOf("PERSON");
		System.out.println(value);

		System.out.println(NodeTypeEnum.PERSON);
	}
}

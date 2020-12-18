package com.hong.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @Author : KongJHong
 * @Date : 2020-12-14 10:45
 * @Version : 1.0
 * Description     :
 */
@SpringBootApplication
@EntityScan("com.hong.neo4j.domain")
public class Neo4jApplication {
	public static void main(String[] args) {
		SpringApplication.run(Neo4jApplication.class);
	}
}

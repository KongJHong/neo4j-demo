package com.hong.neo4j;

import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.mapper.SubsidyGraphMapper;
import com.hong.neo4j.repository.SubsidyRepository;
import com.hong.neo4j.service.SubsidyGraphService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 15:14
 * @Version : 1.0
 * Description     :
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Neo4jApplicationTest {

	@Autowired
	private SubsidyGraphMapper subsidyGraphMapper;


	@Test
	public void test1() {
		List<SubsidyGraph> nodes = subsidyGraphMapper.findAllGraphsIdAndName();
		System.out.println(nodes);
	}

}

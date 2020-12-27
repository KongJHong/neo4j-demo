package com.hong.neo4j.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.neo4j.converter.SubsidyGraphConverter;
import com.hong.neo4j.converter.SubsidyNodeConverter;
import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.dto.SubsidyDTO;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import com.hong.neo4j.dto.SubsidyNodeDTO;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import com.hong.neo4j.mapper.SubsidyGraphMapper;
import com.hong.neo4j.repository.SubsidyRepository;
import com.hong.neo4j.service.SubsidyGraphService;
import com.hong.neo4j.utils.JSONUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 11:39
 * @Version : 1.0
 * Description     :
 */
@Service
public class SubsidyGraphServiceImpl extends ServiceImpl<SubsidyGraphMapper, SubsidyGraph> implements SubsidyGraphService {

	private final SubsidyRepository subsidyRepository;

	private final SubsidyGraphMapper subsidyGraphMapper;

	private final SubsidyGraphConverter subsidyGraphConverter;

	private final SubsidyNodeConverter subsidyNodeConverter;

	private static final Random random = new Random();

	public SubsidyGraphServiceImpl(SubsidyRepository subsidyRepository,
								   SubsidyGraphMapper subsidyGraphMapper,
								   SubsidyGraphConverter subsidyGraphConverter,
								   SubsidyNodeConverter subsidyNodeConverter) {
		this.subsidyGraphMapper = subsidyGraphMapper;
		this.subsidyRepository = subsidyRepository;
		this.subsidyGraphConverter = subsidyGraphConverter;
		this.subsidyNodeConverter = subsidyNodeConverter;
	}

	@Override
	@Transactional
	public void addGraphNode() {
		SubsidyGraph graph = addGraph();
		addNode(graph);
	}


	@Override
	public SubsidyDTO findById(String id) {
		SubsidyDTO dto = new SubsidyDTO();
		SubsidyGraph graph = subsidyGraphMapper.selectById(id);
		assert graph != null;
		dto.setGraphDTO(subsidyGraphConverter.toDTO(graph));
		List<SubsidyNode> nodes = subsidyRepository.findAllByGraphIdAndTitleAndNotEqualWithType(id, graph.getTitle(), SubsidyTypeEnum.NORMAL);
		assert nodes != null;
		dto.setSubsidyNodeDTOS(subsidyNodeConverter.toDTOList(nodes));
		return dto;
	}

	@Override
	public List<SubsidyNodeDTO> findAllPolicyNodeByTitle(String title) {
		List<SubsidyNode> nodes = subsidyRepository.findAllByTypeAndTitle(SubsidyTypeEnum.NORMAL, title);
		return subsidyNodeConverter.toDTOList(nodes);
	}

	@Override
	public List<SubsidyGraphDTO> findAllGraph() {
		List<SubsidyGraph> graphs = subsidyGraphMapper.selectList(null);
		return subsidyGraphConverter.toDTOList(graphs);
	}

	@Override
	@Transactional
	public boolean deleteById(String id) {
		SubsidyGraph graph = subsidyGraphMapper.selectById(id);
		if (graph == null) return false;
		subsidyGraphMapper.deleteById(id);
		subsidyRepository.deleteAllByGraphId(id);
		return true;
	}

	@Override
	public Integer insertBatchGraph(List<SubsidyGraph> graphs) {
		if (graphs == null || graphs.size() == 0) return 0;
		return subsidyGraphMapper.insertBatchSomeColumn(graphs);
	}

	@Override
	public Integer insertBatchNode(List<SubsidyNode> nodes) {
		if (nodes == null || nodes.size() == 0) return 0;
		subsidyRepository.saveAll(nodes);
		return nodes.size();
	}

	@Override
	public Map<String, String> findAllGraphsIdAndName() {
		List<SubsidyGraph> graphs = subsidyGraphMapper.findAllGraphsIdAndName();
		Map<String, String> map = new HashMap<>();
		for (SubsidyGraph graph : graphs) {
			map.put(graph.getName(), graph.getId());
		}
		return map;
	}


	private SubsidyGraph addGraph() {
		SubsidyGraph graph = new SubsidyGraph();
		graph.setTitle("关于全面推开农业\"三项补贴\"改革工作的通知");
		graph.setName("关于全面推开农业\"三项补贴\"改革工作的通知");
		graph.getIndicates().put("type", "政策");
		graph.getIndicates().put("ref", "财农 (2016) 26号");
		graph.getIndicates().put("means", "财政补贴");
		graph.setIndicatesStr(JSONUtils.obj2String(graph.getIndicates()));
		graph.setKeywords("农业,农贸综合补贴,生产建设兵团,补贴,农业信贷担保");
		graph.setContent("很长的内容...................");
		subsidyGraphMapper.insert(graph);
		return graph;
	}

	private void addNode(SubsidyGraph graph) {
		SubsidyNode node = new SubsidyNode("关于全面推开农业\"三项补贴\"改革工作的通知", graph.getId(), 0, new Date(), SubsidyTypeEnum.NORMAL, buildContent(), new String[]{"标签1"});
		SubsidyNode node1 = new SubsidyNode("事件1", graph.getId(), 0, new Date(), SubsidyTypeEnum.EVENT, buildContent(), new String[]{"标签2"});
		SubsidyNode node2 = new SubsidyNode("事件2", graph.getId(), 0, new Date(), SubsidyTypeEnum.EVENT, buildContent(), new String[]{"标签3"});
		node.addPointing(node1, node2);
		SubsidyNode node3 = new SubsidyNode("补贴对象A", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), new String[]{"标签4"});
		SubsidyNode node4 = new SubsidyNode("补贴对象B", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), new String[]{"标签5"});
		SubsidyNode node5 = new SubsidyNode("补贴类型1", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TYPE, buildContent(), new String[]{"标签6"});
		node1.addPointing(node3, node4, node5);
		SubsidyNode node6 = new SubsidyNode("补贴对象C", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), new String[]{"标签7"});
		SubsidyNode node7 = new SubsidyNode("补贴对象D", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), new String[]{"标签8"});
		SubsidyNode node8 = new SubsidyNode("补贴类型2", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TYPE, buildContent(), new String[]{"标签9"});
		node2.addPointing(node6, node7, node8);

		SubsidyNode node9 = new SubsidyNode("某指导意见(政策)", graph.getId(), 0, new Date(), SubsidyTypeEnum.NORMAL, buildContent(), new String[]{"标签10"});
		SubsidyNode node10 = new SubsidyNode("政策A", graph.getId(), 0, new Date(), SubsidyTypeEnum.NORMAL, buildContent(), new String[]{"标签10"});
		node.addPointing(node9);
		node10.addPointing(node);

		graph.setCoreNode(node.getTitle());
		subsidyGraphMapper.updateById(graph);
		subsidyRepository.saveAll(Stream.of(node, node1, node2, node3, node4, node5, node6, node7, node8, node9, node10).collect(Collectors.toList()));
	}

	private String buildContent() {
		String[] contents = {
				"根据国家下达我省市县农业\"三项补贴\"资金规模和市县应补贴面积，2016年全省市耕地地力保护补贴标准为每亩71.46元，其中，秸秆还田，施用农家肥等直接用于耕地力提升的资金要达到每亩10元以上"
		};

		return contents[random.nextInt(contents.length)];

	}


}

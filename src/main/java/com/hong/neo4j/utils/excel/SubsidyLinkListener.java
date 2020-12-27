package com.hong.neo4j.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.repository.SubsidyRepository;
import com.hong.neo4j.service.SubsidyGraphService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 14:48
 * @Version : 1.0
 * Description     : 节点连接 监听器
 */
public class SubsidyLinkListener extends AnalysisEventListener<Map<Integer, String>> {

	private static final int BATCH_COUNT = 5;

	private final SubsidyGraphService subsidyGraphService;

	private final SubsidyRepository subsidyRepository;

	/**
	 * key:名字 value:id
	 */
	private Map<String, String> cacheMaps = new HashMap<>();

	/**
	 * 图谱title, Map(节点title,节点)
	 */
	private final Map<String, Map<String, SubsidyNode>> nodesMap = new HashMap<>();

	private final List<SubsidyNode> list = new ArrayList<>();

	public SubsidyLinkListener(SubsidyGraphService subsidyGraphService,
							   SubsidyRepository subsidyRepository) {
		this.subsidyGraphService = subsidyGraphService;
		this.subsidyRepository = subsidyRepository;
	}


	@Override
	public void invoke(Map<Integer, String> data, AnalysisContext context) {
		String graphTitle = data.get(0);
		String startNodeName = data.get(1);
		String endNodeName = data.get(2);
		String lineContent = data.get(3);

		if (cacheMaps.isEmpty()) initialMaps();

		SubsidyNode startNode = nodesMap.get(graphTitle).get(startNodeName);
		SubsidyNode endNode = nodesMap.get(graphTitle).get(endNodeName);
		endNode.setLineContent(lineContent);
		startNode.addPointing(endNode);
		list.add(startNode);

		if (list.size() >= BATCH_COUNT) {
			subsidyRepository.saveAll(list);
			list.clear();
		}

	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		subsidyRepository.saveAll(list);
	}

	private void initialCacheMaps() {
		cacheMaps = subsidyGraphService.findAllGraphsIdAndName();
	}

	private void initialMaps() {
		initialCacheMaps();
		for (String title : cacheMaps.keySet()) {
			List<SubsidyNode> nodes = this.subsidyRepository.findAllByGraphId(cacheMaps.get(title));
			Map<String, SubsidyNode> map = new HashMap<>();
			for (SubsidyNode node : nodes) {
				map.put(node.getTitle(), node);
			}
			nodesMap.put(title, map);
		}
	}
}

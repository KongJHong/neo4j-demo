package com.hong.neo4j.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import com.hong.neo4j.service.SubsidyGraphService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 14:48
 * @Version : 1.0
 * Description     : 节点监听器
 */
@Slf4j
public class SubsidyNodeListener extends AnalysisEventListener<Map<Integer, String>> {

	private static final int BATCH_COUNT = 5;

	List<SubsidyNode> list = new ArrayList<>();

	private final SubsidyGraphService subsidyGraphService;

	/** key:名字 value:id */
	private Map<String, String> cacheMaps = new HashMap<>();

	public SubsidyNodeListener(SubsidyGraphService subsidyGraphService) {
		this.subsidyGraphService = subsidyGraphService;
	}


	@Override
	public void invoke(Map<Integer, String> data, AnalysisContext context) {
		if (cacheMaps.isEmpty()) {
			initialCacheMaps();
		}
		String graphTitle = data.get(0);
		String graphId = cacheMaps.get(graphTitle);
		String nodeTitle = data.get(1);
		String nodeWeight = data.get(2);
		String nodeType = data.get(3);
		String nodeContent = data.get(4);
		String nodeTags = StringUtils.isNotBlank(data.get(5))?data.get(5) : "";
		SubsidyNode node = new SubsidyNode(nodeTitle, graphId, Integer.parseInt(nodeWeight), new Date(), SubsidyTypeEnum.valueOf(nodeType), nodeContent, "",nodeTags.split(","));
		list.add(node);
		if (list.size() >= BATCH_COUNT) {
			// 保存
			subsidyGraphService.insertBatchNode(list);
			list.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		subsidyGraphService.insertBatchNode(list);
		cacheMaps.clear();
	}

	private void initialCacheMaps() {
		cacheMaps = subsidyGraphService.findAllGraphsIdAndName();
	}
}

package com.hong.neo4j.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.service.SubsidyGraphService;
import com.hong.neo4j.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 11:29
 * @Version : 1.0
 * Description     : 绝对不能用spring代理
 */
@Slf4j
public class SubsidyGraphListener extends AnalysisEventListener<SubsidyGraph> {

	private static final int BATCH_COUNT = 5;

	private final SubsidyGraphService subsidyGraphService;

	List<SubsidyGraph> list = new ArrayList<>();

	public SubsidyGraphListener(SubsidyGraphService subsidyGraphService) {
		this.subsidyGraphService = subsidyGraphService;
	}

	@Override
	public void invoke(SubsidyGraph data, AnalysisContext context) {
		log.info("解析到一条数据:{}", JSONUtils.obj2String(data));
		list.add(data);
		if (list.size() >= BATCH_COUNT) {
			subsidyGraphService.insertBatchGraph(list);
			list.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("所有数据解析完成");
		subsidyGraphService.insertBatchGraph(list);
	}
}

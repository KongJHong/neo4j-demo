package com.hong.neo4j.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hong.neo4j.utils.excel.DemoData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 9:12
 * @Version : 1.0
 * Description     : excel工具类
 */
@Slf4j
public class ExcelUtils extends AnalysisEventListener<Map<Integer, String>> {

	private static final int BATCH_COUNT = 5;

	List<Map<Integer, String>> list = new ArrayList<>();

	@Override
	public void invoke(Map<Integer, String> demoData, AnalysisContext analysisContext) {
		log.info("解析到一条数据:{}", JSONUtils.obj2String(demoData));
		list.add(demoData);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		log.info("解析完成");
		try {
			TimeUnit.SECONDS.sleep(1);
			log.info("休眠完成--------------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

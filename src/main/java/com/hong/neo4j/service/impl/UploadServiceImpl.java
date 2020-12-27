package com.hong.neo4j.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.mapper.SubsidyGraphMapper;
import com.hong.neo4j.repository.SubsidyRepository;
import com.hong.neo4j.service.SubsidyGraphService;
import com.hong.neo4j.service.UploadService;
import com.hong.neo4j.utils.excel.SubsidyGraphListener;
import com.hong.neo4j.utils.excel.SubsidyLinkListener;
import com.hong.neo4j.utils.excel.SubsidyNodeListener;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 13:47
 * @Version : 1.0
 * Description     :
 */
@Service
public class UploadServiceImpl implements UploadService {

	private final SubsidyGraphService subsidyGraphService;

	private final SubsidyRepository subsidyRepository;

	public UploadServiceImpl(SubsidyGraphService subsidyGraphService,
							 SubsidyRepository subsidyRepository) {
		this.subsidyGraphService = subsidyGraphService;
		this.subsidyRepository = subsidyRepository;
	}

	@Override
	public boolean processUploading(InputStream inputStream) {
		ExcelReader excelReader = null;
		try{
		 	excelReader = EasyExcel.read(inputStream).build();
		 	ReadSheet readSheet = EasyExcel.readSheet(0).head(SubsidyGraph.class).registerReadListener(new SubsidyGraphListener(subsidyGraphService)).build();
		 	ReadSheet readSheet1 = EasyExcel.readSheet(1).registerReadListener(new SubsidyNodeListener(subsidyGraphService)).build();
		 	ReadSheet readSheet2 = EasyExcel.readSheet(2).registerReadListener(new SubsidyLinkListener(subsidyGraphService,subsidyRepository)).build();

		 	excelReader.read(readSheet, readSheet1, readSheet2);
		}catch (Exception e){
		    e.printStackTrace();
		    return false;
		}finally {
		    if (excelReader != null)excelReader.finish();
		}
		return true;
	}
}

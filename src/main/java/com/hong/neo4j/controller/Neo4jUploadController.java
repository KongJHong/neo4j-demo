package com.hong.neo4j.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.service.UploadService;
import com.hong.neo4j.utils.excel.SubsidyGraphListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author : KongJHong
 * @Date : 2020-12-24 15:56
 * @Version : 1.0
 * Description     : 文件导入控制器
 */
@RestController
@RequestMapping("/upload")
public class Neo4jUploadController {

	private final UploadService uploadService;

	public Neo4jUploadController(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@PostMapping("/")
	public String upload(MultipartFile file) throws IOException {
		uploadService.processUploading(file.getInputStream());
		return "null";
	}
}

package com.hong.neo4j;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.hong.neo4j.utils.ExcelUtils;
import com.hong.neo4j.utils.TestFileUtils;
import com.hong.neo4j.utils.excel.DemoData;
import org.junit.Test;

import java.io.File;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 11:00
 * @Version : 1.0
 * Description     :
 */
public class SimpleReadTest {


	@Test
	public void testRead() {
		String fileName = TestFileUtils.getPath() + "demo" + File.separator + "demo.xlsx";
		ExcelReader excelReader = null;
		try{
		 	excelReader = EasyExcel.read(fileName, new ExcelUtils()).build();
			ReadSheet readSheet = EasyExcel.readSheet(0).build();
			ReadSheet readSheet1 = EasyExcel.readSheet(1).build();
			ReadSheet readSheet2 = EasyExcel.readSheet(2).build();
			ReadSheet readSheet3 = EasyExcel.readSheet(3).build();
			excelReader.read(readSheet, readSheet1, readSheet2, readSheet3);
		}catch (Exception e){
		    e.printStackTrace();
		}finally {
		    if (excelReader != null)excelReader.finish();
		}
	}
}

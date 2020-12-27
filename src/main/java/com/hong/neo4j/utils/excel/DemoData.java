package com.hong.neo4j.utils.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 10:44
 * @Version : 1.0
 * Description     :
 */
@Data
public class DemoData {

	@ExcelProperty("字符串标题")
	private String string;

	@ExcelProperty("日期标题")
	private Date date;

	@ExcelProperty("数字标题")
	private Double doubleData;
}

package com.hong.neo4j.utils;

import java.io.File;
import java.io.InputStream;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 10:55
 * @Version : 1.0
 * Description     : excel测试文件读取工具类
 */
public class TestFileUtils {

	public static InputStream getResourcesFileInputStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
	}

	public static String getPath() {
		return TestFileUtils.class.getResource("/").getPath();
	}

	public static File readFile(String pathName) {
		return new File(getPath() + pathName);
	}

	public static File readUserHomeFile(String pathName) {
		return new File(System.getProperty("user.home") + File.separator + pathName);
	}
}

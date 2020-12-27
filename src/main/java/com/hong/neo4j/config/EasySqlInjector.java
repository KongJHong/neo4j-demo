package com.hong.neo4j.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 14:03
 * @Version : 1.0
 * Description     : 自定义数据方法注入
 */
public class EasySqlInjector extends DefaultSqlInjector {

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		List<AbstractMethod> methods = super.getMethodList(mapperClass);
		methods.add(new InsertBatchSomeColumn());
		return methods;
	}
}

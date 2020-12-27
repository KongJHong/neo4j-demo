package com.hong.neo4j.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 15:44
 * @Version : 1.0
 * Description     : 自动注入
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("createdBy", "KongJHong", metaObject);
        this.setFieldValByName("lastUpdateBy", "KongJHong", metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
		this.setFieldValByName("lastUpdateBy", "KongJHong", metaObject);
	}
}

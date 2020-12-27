package com.hong.neo4j.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 14:09
 * @Version : 1.0
 * Description     : 扩展BaseMapper
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

	Integer insertBatchSomeColumn(List<T> entities);
}

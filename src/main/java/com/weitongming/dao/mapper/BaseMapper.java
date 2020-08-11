package com.weitongming.dao.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author weitongming
 * @Classname BaseMapper
 * @Description TODO
 * @Date 2020/1/13 15:34
 * @Version V1.0
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}

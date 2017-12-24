package net.sppan.base.common;

import java.io.Serializable;

import net.sppan.base.entity.support.BaseEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 多个MAPPER的集成
 *
 * @author zxc
 * @since 2017-07-06 21:31
 */
public interface Mappers<T extends BaseEntity, ID extends Serializable> extends Mapper<T>, MySqlMapper<T> {
    //特别注意，该接口不能被扫描到，否则会出错
}

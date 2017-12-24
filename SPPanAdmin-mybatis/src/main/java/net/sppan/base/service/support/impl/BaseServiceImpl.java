package net.sppan.base.service.support.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import net.sppan.base.common.Mappers;
import net.sppan.base.entity.support.BaseEntity;
import net.sppan.base.service.support.IBaseService;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements IBaseService<T, ID>{

	public abstract Mappers<T, ID> getMappers();
	
	@Override
	public T selectOne(T record) {
		
		return getMappers().selectOne(record);
	}

	@Override
	public List<T> select(T record) {
		
		return getMappers().select(record);
	}

	@Override
	public List<T> selectAll() {
		
		return getMappers().selectAll();
	}

	@Override
	public int selectCount(T record) {
		
		return getMappers().selectCount(record);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		
		return getMappers().selectByPrimaryKey(key);
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		
		return getMappers().existsWithPrimaryKey(key);
	}

	@Override
	public List<T> selectByExample(Object example) {
		
		return getMappers().selectByExample(example);
	}

	@Override
	public int selectCountByExample(Object example) {
		
		return getMappers().selectCountByExample(example);
	}

	@Override
	public int insert(T record) {
		
		return getMappers().insert(record);
	}

	@Override
	public int insertSelective(T record) {
		
		return getMappers().insert(record);
	}

	@Override
	public int insertList(List<T> recordList) {
		
		return getMappers().insertList(recordList);
	}

	@Override
	public int insertUseGeneratedKeys(T record) {
		
		return getMappers().insertUseGeneratedKeys(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		
		return getMappers().updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		
		return getMappers().updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByExample(T record, Object example) {
		
		return getMappers().updateByExample(record, example);
	}

	@Override
	public int updateByExampleSelective(T record, Object example) {
		
		return getMappers().updateByExampleSelective(record, example);
	}

	@Override
	public int delete(T record) {
		
		return getMappers().delete(record);
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		
		return getMappers().deleteByPrimaryKey(key);
	}

	@Override
	public int deleteByExample(Object example) {
		
		return getMappers().deleteByExample(example);
	}

	
}

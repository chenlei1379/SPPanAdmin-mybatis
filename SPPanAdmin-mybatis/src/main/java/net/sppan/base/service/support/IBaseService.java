package net.sppan.base.service.support;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IBaseService<T, ID extends Serializable>{
	
	T selectOne(T record);
	List<T> select(T record);
	List<T> selectAll();
	int selectCount(T record);
	T selectByPrimaryKey(Object key);
	boolean existsWithPrimaryKey(Object key);
	
	List<T> selectByExample(Object example);
	int selectCountByExample(Object example);
	
	
	
	int insert(T record);
	int insertSelective(T record);
	int insertList(List<T> recordList);
	int insertUseGeneratedKeys(T record);
	
	int updateByPrimaryKey(T record);
	int updateByPrimaryKeySelective(T record);
	
	int updateByExample(@Param("record") T record, @Param("example") Object example);
	int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);
	
	int delete(T record);
	
	int deleteByPrimaryKey(Object key);
	int deleteByExample(Object example);
}

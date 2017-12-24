package net.sppan.base.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.sppan.base.common.Mappers;
import net.sppan.base.entity.Resource;

@Component
public interface SysMenuMapper extends Mappers<Resource, Integer> {

	void deleteGrant( Integer id);
	


	List<Resource> findAllByOrderByParentAscIdAscSortAsc();
	
	/**
	 * 根据roleId得到Menu列表
	 * @param roleId
	 * @return
	 */
	List<Resource> findMenuByRoleId(Integer roleId);

}

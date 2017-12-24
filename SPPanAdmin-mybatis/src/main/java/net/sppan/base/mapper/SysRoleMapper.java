package net.sppan.base.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;

import net.sppan.base.common.Mappers;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.RoleResource;
import net.sppan.base.entity.UserRole;

@Component
public interface SysRoleMapper extends Mappers<Role, Integer> {
	
	PageInfo<Role> findAllByNameContainingOrDescriptionContaining(String searchText1,String searchText2, PageInfo pageable);
	
	
	List<Role> findRoleListByUserId(Integer userid);
	
	/**
	 * 为角色赋权限时批量插入
	 * @param list
	 * @return
	 */
	Integer insertRoleResource(List<RoleResource> list);
	
	/**
	 * 为用户赋值时批量插入
	 * @param list
	 * @return
	 */
	Integer insertUserRole(List<UserRole> list);
	
	/**
	 * 删除role_user关系表
	 * @param roleId
	 * @return
	 */
	Integer deleteRoleUser(Integer roleId);
	/**
	 * 删除role_resource关系表
	 * @param id
	 * @return
	 */
	Integer deleteRoleResource(Integer roleId);

}

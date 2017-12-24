package net.sppan.base.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import net.sppan.base.entity.Resource;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.RoleResource;
import net.sppan.base.entity.UserRole;
import net.sppan.base.service.support.IBaseService;

/**
 * <p>
 * 角色服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface IRoleService extends IBaseService<Role, Integer>{

	/**
	 * 添加或者修改角色
	 * @param role
	 */
	void saveOrUpdate(Role role);
	int delete(Integer id);
	/**
	 * 给角色分配资源
	 * @param id 角色ID
	 * @param resourceIds 资源ids
	 */
	void grant(Integer id, String[] resourceIds);

	public PageInfo<Role> findByPage(String searchText, Integer pageNum,Integer pageSize);
	
	/**
	 * 根据userid查询出所属用户组
	 * @param userid
	 * @return
	 */
	List<Role> findRoleListByUserId(Integer userid);
	
	Integer insertRoleResource(List<RoleResource> list);
	
	/**
	 * 为用户赋值时批量插入
	 * @param list
	 * @return
	 */
	Integer insertUserRole(List<UserRole> list);
	
}

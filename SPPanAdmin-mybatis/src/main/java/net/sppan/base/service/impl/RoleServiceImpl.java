package net.sppan.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sppan.base.common.Constats;
import net.sppan.base.common.Mappers;
import net.sppan.base.entity.Resource;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.RoleResource;
import net.sppan.base.entity.User;
import net.sppan.base.entity.UserRole;
import net.sppan.base.mapper.SysRoleMapper;
import net.sppan.base.service.IResourceService;
import net.sppan.base.service.IRoleService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * <p>
 * 角色表  服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements IRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private IResourceService resourceService;
	
	

	@Override
	public void saveOrUpdate(Role role) {
		if(role.getId() != null){
			Role dbRole = selectByPrimaryKey(role.getId());
			dbRole.setUpdateTime(new Date());
			dbRole.setName(role.getName());
			dbRole.setDescription(role.getDescription());
			dbRole.setUpdateTime(new Date());
			dbRole.setStatus(role.getStatus());
			sysRoleMapper.updateByPrimaryKey(dbRole);
		}else{
			role.setCreateTime(new Date());
			role.setUpdateTime(new Date());
			sysRoleMapper.insert(role);
		}
	}

	
	/**
	 * 角色删除时：
	 * 1.需要将user_role关系表删除
	 * 2.需要将role_resource关系表删除
	 */
	@Override
	public int delete(Integer id) {
		Role role = sysRoleMapper.selectByPrimaryKey(id);
		Assert.state(!"administrator".equals(role.getRoleKey()),"超级管理员角色不能删除");
		sysRoleMapper.deleteRoleUser(id);
		sysRoleMapper.deleteRoleResource(id);
		return sysRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value = Constats.RESOURCECACHENAME, key = "'tree_' + #id")
	public void grant(Integer id, String[] resourceIds) {
		Role role = selectByPrimaryKey(id);
		Assert.notNull(role, "角色不存在");
		
		Assert.state(!"administrator".equals(role.getRoleKey()),"超级管理员角色不能进行资源分配");
		
		List<RoleResource> list = new ArrayList<RoleResource>();
		for (int i = 0; i < resourceIds.length; i++) {
			if(StringUtils.isNotBlank(resourceIds[i]) && !"0".equals(resourceIds[i])){
				RoleResource r = new RoleResource(id,Integer.valueOf(resourceIds[i]));
				list.add(r);
			}
		}
		sysRoleMapper.insertRoleResource(list);
		/*
		Resource resource;
		Set<Resource> resources = new HashSet<Resource>();
		if(resourceIds != null){
			for (int i = 0; i < resourceIds.length; i++) {
				if(StringUtils.isBlank(resourceIds[i]) || "0".equals(resourceIds[i])){
					continue;
				}
				Integer rid = Integer.parseInt(resourceIds[i]);
				resource = resourceService.selectByPrimaryKey(rid);
				resources.add(resource);
			}
		}
		role.setResources(resources);
		sysRoleMapper.updateByPrimaryKey(role);*/
	}

	@Override
	public PageInfo<Role> findByPage(String searchText, Integer pageNum,Integer pageSize) {
		Example example = new Example(User.class);
		if(StringUtils.isNotBlank(searchText)){
			example.createCriteria().andLike("name", "%"+searchText+"%");
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list =sysRoleMapper.selectByExample(example);
		PageInfo<Role> info = new PageInfo<Role>(list);
		return info;
	}



	



	@Override
	public Mappers<Role, Integer> getMappers() {
		// TODO Auto-generated method stub
		return sysRoleMapper;
	}



	@Override
	public List<Role> findRoleListByUserId(Integer userid) {
		return sysRoleMapper.findRoleListByUserId(userid);
	}



	@Override
	public Integer insertRoleResource(List<RoleResource> list) {
		return sysRoleMapper.insertRoleResource(list);
	}


	@Override
	public Integer insertUserRole(List<UserRole> list) {
		return sysRoleMapper.insertUserRole(list);
	}
	
}

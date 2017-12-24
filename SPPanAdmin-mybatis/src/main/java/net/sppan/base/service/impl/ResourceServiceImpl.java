package net.sppan.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sppan.base.common.Constats;
import net.sppan.base.common.Mappers;
import net.sppan.base.entity.Resource;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.User;
import net.sppan.base.mapper.SysMenuMapper;
import net.sppan.base.service.IResourceService;
import net.sppan.base.service.IRoleService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import net.sppan.base.vo.ZtreeView;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Integer>
		implements IResourceService {

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private IRoleService roleService;

	

	@Override
	@Cacheable(value=Constats.RESOURCECACHENAME,key="'tree_' + #roleId")
	public List<ZtreeView> tree(int roleId) {
		List<ZtreeView> resulTreeNodes = new ArrayList<ZtreeView>();
		List<Resource> roleResources = sysMenuMapper.findMenuByRoleId(roleId);
		List<Integer> roleResourceIds = new ArrayList<Integer>();
		for (int i = 0; i < roleResources.size(); i++) {
			roleResourceIds.add(roleResources.get(i).getId());
		}
		resulTreeNodes.add(new ZtreeView(0L, null, "系统菜单", true));
		ZtreeView node;
		Example example = new Example(Resource.class);
		example.setOrderByClause("parent_id asc,id asc, sort asc");
		List<Resource> all = sysMenuMapper.selectByExample(example);
		for (Resource resource : all) {
			node = new ZtreeView();
			node.setId(Long.valueOf(resource.getId()));
			if (resource.getParentId() == null) {
				node.setpId(0L);
			} else {
				node.setpId(Long.valueOf(resource.getParentId()));
			}
			node.setName(resource.getName());
			if (roleResources != null && roleResourceIds.contains(resource.getId())) {
				node.setChecked(true);
			}
			resulTreeNodes.add(node);
		}
		return resulTreeNodes;
	}

	@Override
	public void saveOrUpdate(Resource resource) {
		if(resource.getId() != null){
			Resource dbResource = sysMenuMapper.selectByPrimaryKey(resource.getId());
			dbResource.setUpdateTime(new Date());
			dbResource.setName(resource.getName());
			dbResource.setSourceKey(resource.getSourceKey());
			dbResource.setType(resource.getType());
			dbResource.setSourceUrl(resource.getSourceUrl());
			dbResource.setLevel(resource.getLevel());
			dbResource.setSort(resource.getSort());
			dbResource.setIsHide(resource.getIsHide());
			dbResource.setIcon(resource.getIcon());
			dbResource.setDescription(resource.getDescription());
			dbResource.setUpdateTime(new Date());
			dbResource.setParent(resource.getParent());
			sysMenuMapper.updateByPrimaryKey(dbResource);
		}else{
			resource.setCreateTime(new Date());
			resource.setUpdateTime(new Date());
			sysMenuMapper.insert(resource);
		}
	}

	@Override
	public int delete(Integer id) {
		sysMenuMapper.deleteGrant(id);
		return sysMenuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public PageInfo<Resource> findByPage(String searchText, Integer pageNum,Integer pageSize) {
		Example example = new Example(User.class);
		if(StringUtils.isNotBlank(searchText)){
			example.createCriteria().andLike("name", "%"+searchText+"%");
		}
		PageHelper.startPage(pageNum, pageSize);
		
		
		List<Resource> list =sysMenuMapper.selectByExample(example);
		PageInfo<Resource> info = new PageInfo<Resource>(list);
		return info;
	}

	@Override
	public Mappers<Resource, Integer> getMappers() {
		return this.sysMenuMapper;
	}

	@Override
	public List<Resource> findMenuByRoleId(Integer roleId) {
		return sysMenuMapper.findMenuByRoleId(roleId);
	}

	@Override
	public List<Resource> findMenuByTypeAndOrder() {
		
		Example example = new Example(Resource.class);
		/*List<Integer> typeInList = new ArrayList<Integer>();
		typeInList.add(0);
		typeInList.add(1);*/
		example.createCriteria().andEqualTo("type", 0);
		example.setOrderByClause("type asc,sort asc");
		// 目录
		List<Resource> catalogueList = sysMenuMapper.selectByExample(example);
		// 菜单
		example.createCriteria().andEqualTo("type", 1);
		List<Resource> menuList = sysMenuMapper.selectByExample(example);
		
		
		return null;
	}
	
	private void getMenuLevels(Resource resource,List<Resource> list,List<Resource> newList){
		newList.add(resource);
		for (int i = 0; i < list.size(); i++) {
			if(resource.getId()==list.get(i).getParentId()){
				newList.add(resource);
			}
		}
	}
	
}

package net.sppan.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sppan.base.common.Mappers;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.entity.Resource;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.User;
import net.sppan.base.entity.UserRole;
import net.sppan.base.mapper.SysUserMapper;
import net.sppan.base.service.IRoleService;
import net.sppan.base.service.IUserService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * <p>
 * 用户账户表  服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private IRoleService roleServiceImpl;
	
	

	@Override
	public User findByUserName(String username) {
		
		User user = null;
		try {
			user = sysUserMapper.findByUserName(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void saveOrUpdate(User user) {
		if(user.getId() != null){
			User dbUser = selectByPrimaryKey(user.getId());
			dbUser.setNickName(user.getNickName());
			dbUser.setSex(user.getSex());
			dbUser.setBirthday(user.getBirthday());
			dbUser.setTelephone(user.getTelephone());
			dbUser.setEmail(user.getEmail());
			dbUser.setAddress(user.getAddress());
			dbUser.setLocked(user.getLocked());
			dbUser.setDescription(user.getDescription());
			dbUser.setUpdateTime(new Date());
			updateByPrimaryKey(dbUser);
		}else{
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setDeleteStatus(0);
			user.setPassword(MD5Utils.md5("111111"));
			insert(user);
		}
	}
	
	

	@Override
	public int delete(Integer id) {
		User user = selectByPrimaryKey(id);
		Assert.state(!"admin".equals(user.getUserName()),"超级管理员用户不能删除");
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void grant(Integer id, String[] roleIds) {
		User user = selectByPrimaryKey(id);
		Assert.notNull(user, "用户不存在");
		Assert.state(!"admin".equals(user.getUserName()),"超级管理员用户不能修改管理角色");
		Role role;
		List<Role> roles = new ArrayList<Role>();
		if(roleIds != null){
			List<UserRole> list = new ArrayList<UserRole>();
			for (int i = 0; i < roleIds.length; i++) {
				UserRole r = new UserRole(id, Integer.parseInt(roleIds[i]));
				list.add(r);
			}
			roleServiceImpl.insertUserRole(list);
		}
		
		
	}

	@Override
	public PageInfo<User> findByPage(String searchText, Integer pageNum,Integer pageSize) {
		
		Example example = new Example(User.class);
		if(StringUtils.isNotBlank(searchText)){
			example.createCriteria().andLike("userName", "%"+searchText+"%");
		}
		PageHelper.startPage(pageNum, pageSize);
		List<User> list =sysUserMapper.selectByExample(example);
		PageInfo<User> info = new PageInfo<User>(list);
		return info;
	}

	
	@Override
	public void updatePwd(User user, String oldPassword, String password1, String password2) {
		Assert.notNull(user, "用户不能为空");
		Assert.notNull(oldPassword, "原始密码不能为空");
		Assert.notNull(password1, "新密码不能为空");
		Assert.notNull(password2, "重复密码不能为空");
		
		User dbUser = sysUserMapper.findByUserName(user.getUserName());
		Assert.notNull(dbUser, "用户不存在");
		
		Assert.isTrue(user.getPassword().equals(MD5Utils.md5(oldPassword)), "原始密码不正确");;
		Assert.isTrue(password1.equals(password2), "两次密码不一致");
		dbUser.setPassword(MD5Utils.md5(password1));
		sysUserMapper.updateByPrimaryKey(dbUser);
	}

	@Override
	public Mappers<User, Integer> getMappers() {
		return sysUserMapper;
	}
	
}

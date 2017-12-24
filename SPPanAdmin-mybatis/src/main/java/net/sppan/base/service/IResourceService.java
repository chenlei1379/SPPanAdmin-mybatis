package net.sppan.base.service;

import net.sppan.base.entity.Resource;
import net.sppan.base.service.support.IBaseService;
import net.sppan.base.vo.ZtreeView;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 资源服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface IResourceService extends IBaseService<Resource, Integer>{

	/**
	 * 获取角色的权限树
	 * @param roleId
	 * @return
	 */
	List<ZtreeView> tree(int roleId);
	int delete(Integer id);
	/**
	 * 修改或者新增资源
	 * @param resource
	 */
	void saveOrUpdate(Resource resource);

	public PageInfo<Resource> findByPage(String searchText, Integer pageNum,Integer pageSize);
	
	/**
	 * 根据roleId得到Menu列表
	 * @param roleId
	 * @return
	 */
	List<Resource> findMenuByRoleId(Integer roleId);
	/**
	 * 只查询出type值为0、1的值并排序
	 * @return
	 */
	List<Resource> findMenuByTypeAndOrder();
}

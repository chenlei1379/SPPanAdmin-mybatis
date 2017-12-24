package net.sppan.base.controller.admin.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.User;
import net.sppan.base.service.IRoleService;
import net.sppan.base.service.IUserService;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/user/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public PageInfo<User> list(
			@RequestParam(value="searchText",required=false) String searchText,@RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "15") Integer pageSize
			) {
		PageInfo<User> page = userService.findByPage(searchText, pageNumber, pageSize);
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/user/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		User user = userService.selectByPrimaryKey(id);
		map.put("user", user);
		return "admin/user/form";
	}
	
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(User user,ModelMap map){
		try {
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure("修改失败");
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			userService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure("删除失败");
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	public String grant(@PathVariable Integer id, ModelMap map) {
		User user = userService.selectByPrimaryKey(id);
		map.put("user", user);
		
		List<Role> set = roleService.findRoleListByUserId(id);
		List<Integer> roleIds = new ArrayList<Integer>();
		for (Role role : set) {
			roleIds.add(role.getId());
		}
		map.put("roleIds", roleIds);
		
		List<Role> roles = roleService.selectAll();
		map.put("roles", roles);
		return "admin/user/grant";
	}
	
	@ResponseBody
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.POST)
	public JsonResult grant(@PathVariable Integer id,String[] roleIds, ModelMap map) {
		try {
			userService.grant(id,roleIds);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure("修改权限组失败");
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
	public String updatePwd() {
		return "admin/user/updatePwd";
	}
	
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updatePwd(String oldPassword, String password1, String password2) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Object principal = subject.getPrincipal();
			if(principal== null){
				return JsonResult.failure("您尚未登录");
			}
			userService.updatePwd((User)principal, oldPassword, password1, password2);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
}

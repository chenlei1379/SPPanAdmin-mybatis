package net.sppan.base.controller.admin.system;

import java.util.ArrayList;
import java.util.List;

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
import net.sppan.base.entity.Resource;
import net.sppan.base.service.IResourceService;
import net.sppan.base.vo.ZtreeView;
import tk.mybatis.mapper.entity.Example;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController {
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping("/tree/{resourceId}")
	@ResponseBody
	public List<ZtreeView> tree(@PathVariable Integer resourceId){
		List<ZtreeView> list = resourceService.tree(resourceId);
		return list;
	}
	
	@RequestMapping("/index")
	public String index() {
		return "admin/resource/index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Resource> list(
			@RequestParam(value="searchText",required=false) String searchText,@RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "15") Integer pageSize
			) {
		PageInfo<Resource> page = resourceService.findByPage(searchText,pageNumber,pageSize);
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		List<Resource> list = resourceService.selectAll();
		map.put("list", list);
		return "admin/resource/form";
	}
	

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Resource resource = resourceService.selectByPrimaryKey(id);
		map.put("resource", resource);
		
		List<Resource> list = resourceService.findMenuByTypeAndOrder();
		
		
		map.put("list", list);
		return "admin/resource/form";
	}
	
	@RequestMapping(value= {"/edit"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Resource resource,ModelMap map){
		try {
			resourceService.saveOrUpdate(resource);
		} catch (Exception e) {
			return JsonResult.failure("修改失败");
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			resourceService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure("删除失败！或正在使用不能删除");
		}
		return JsonResult.success();
	}
}

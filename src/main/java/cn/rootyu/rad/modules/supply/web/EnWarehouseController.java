/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.web;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.supply.entity.EnWarehouse;
import cn.rootyu.rad.modules.supply.entity.EnWarehouseZ;
import cn.rootyu.rad.modules.supply.service.EnWarehouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入库单Controller
 * @author maliang
 * @version 2015-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/supply/enWarehouse")
public class EnWarehouseController extends BaseController {

	@Autowired
	private EnWarehouseService enWarehouseService;
	
	/**
	 * 主表列表
	 * @author maliang
	 * @version 2015-10-17
	 */
	@RequiresPermissions("supply:enWarehouse:view")
	@RequestMapping(value = {"index", ""})
	public String index() {
		return "modules/supply/enWarehouseList";
	}
	
	/**
	 * 主表列表数据
	 * @author maliang
	 * @version 2015-10-17
	 */
	@RequiresPermissions("supply:enWarehouse:view")
	@ResponseBody
	@RequestMapping(value = {"list"})
	public Map<String,Object> list(EnWarehouse enWarehouse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EnWarehouse> page = enWarehouseService.findPage(new Page<EnWarehouse>(request, response), enWarehouse);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表
	 * @author maliang
	 * @version 2015-10-17
	 */
	@RequiresPermissions("supply:enWarehouse:view")
	@ResponseBody
	@RequestMapping(value = {"childList"})
	public Map<String,List<EnWarehouseZ>> childList(String pid, HttpServletRequest request, HttpServletResponse response) {
		EnWarehouseZ entity = new EnWarehouseZ();
		entity.setPkEwhid(pid);
		Map<String,List<EnWarehouseZ>> l = enWarehouseService.findChildList(new Page<EnWarehouseZ>(request, response,-1), entity);
		return l;
	}

	/**
	 * 生成入库单
	 * @author maliang
	 * @version 2015-10-23
	 * update 2015-10-26
	 */
//	@RequiresPermissions("supply:enWarehouse:addData")
//	@ResponseBody
//	@RequestMapping(value = "addData")
//	public Map<String, String> addData(String arriveOrderId) {
//		Map<String, String> m = new HashMap<String, String>();
//		enWarehouseService.setErrmsg("数据异常！");
//		try {
//			boolean b = enWarehouseService.addData(arriveOrderId);
//			if (b) {
//				m.put("status", "1");
//				m.put("msg", "入库单生成成功");
//			}else{
//				m.put("status", "0");
//				m.put("msg", enWarehouseService.getErrmsg());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			m.put("status", "0");
//			m.put("msg", enWarehouseService.getErrmsg());
//		}
//		return m;
//	}

	/**
	 * 修改入库单
	 * @author maliang
	 * @version 2015-10-28
	 */
	@RequiresPermissions("supply:enWarehouse:saveData")
	@ResponseBody
	@RequestMapping(value = "saveData")
	public Map<String, String> saveData(EnWarehouse entity) {
		Map<String, String> m = new HashMap<String, String>();
		enWarehouseService.setErrmsg("数据异常！");
		try {
			boolean b = enWarehouseService.saveData(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "修改成功");
			}else{
				m.put("status", "0");
				m.put("msg", enWarehouseService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", enWarehouseService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 确认入库单
	 * @author maliang
	 * @version 2015-10-31
	 */
	@RequiresPermissions("supply:enWarehouse:confirmSave")
	@ResponseBody
	@RequestMapping(value = "confirmSave")
	public Map<String, String> confirmSave(EnWarehouse entity) {
		Map<String, String> m = new HashMap<String, String>();
		enWarehouseService.setErrmsg("数据异常！");
		try {
			boolean b = enWarehouseService.confirmSave(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "下发成功");
			}else{
				m.put("status", "0");
				m.put("msg", enWarehouseService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", enWarehouseService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 取消下发入库单
	 * @author maliang
	 * @version 2016-03-04
	 */
	@RequiresPermissions("supply:enWarehouse:confirmSaveCancel")
	@ResponseBody
	@RequestMapping(value = "confirmSaveCancel")
	public Map<String, String> confirmSaveCancel(EnWarehouse entity) {
		Map<String, String> m = new HashMap<String, String>();
		enWarehouseService.setErrmsg("数据异常！");
		try {
			boolean b = enWarehouseService.confirmSaveCancel(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "操作成功");
			}else{
				m.put("status", "0");
				m.put("msg", enWarehouseService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", enWarehouseService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 删除单据
	 * @author maliang
	 * @version 2016-3-7
	 */
//	@RequiresPermissions("supply:enWarehouse:delete")
//	@ResponseBody
//	@RequestMapping(value = "delete",method = RequestMethod.POST)
//	public Map<String, String> delete(EnWarehouse entity) {
//		Map<String, String> m = new HashMap<String, String>();
//		enWarehouseService.setErrmsg("数据异常！");
//		try {
//			boolean b = enWarehouseService.delete(entity);
//			if (b) {
//				m.put("status", "1");
//				m.put("msg", "操作成功！");
//			}else{
//				m.put("status", "0");
//				m.put("msg", enWarehouseService.getErrmsg());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			m.put("status", "0");
//			m.put("msg", enWarehouseService.getErrmsg());
//		}
//		return m;
//	}
	
}
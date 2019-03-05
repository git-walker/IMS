/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.web;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.supply.entity.ArriveOrder;
import cn.rootyu.rad.modules.supply.entity.ArriveOrderB;
import cn.rootyu.rad.modules.supply.service.ArriveOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 到货单Controller
 * @author maliang
 * @version 2015-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/supply/arriveOrder")
public class ArriveOrderController extends BaseController {

	@Autowired
	private ArriveOrderService arriveOrderService;
	
	/**
	 * 到货单列表(保供人员)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4bg")
	@RequestMapping(value = {"index4bg"})
	public String index4bg(Model model) {
		return "modules/supply/arriveOrderList4bg";
	}
	
	/**
	 * 到货单列表数据(保供人员)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4bg")
	@ResponseBody
	@RequestMapping(value = {"list4bg"})
	public Map<String,Object> list4bg(ArriveOrder arriveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArriveOrder> page = arriveOrderService.findPage(new Page<ArriveOrder>(request, response), arriveOrder, ArriveOrderService.DATA4BG);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表(保供人员)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4bg")
	@ResponseBody
	@RequestMapping(value = {"childList4bg"})
	public Map<String,Object> childList4bg(String pid,HttpServletRequest request, HttpServletResponse response) {
		ArriveOrderB entity = new ArriveOrderB();
		entity.setCarriveorderid(pid);
		List<ArriveOrderB> l = arriveOrderService.findChildList(new Page<ArriveOrderB>(request, response,-1),entity, ArriveOrderService.DATA4BG);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 到货单列表(入库管理员，预知判断)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4rkStep1")
	@RequestMapping(value = {"index4rkStep1"})
	public String index4rkStep1(Model model) {
		return "modules/supply/arriveOrderList4rkStep1";
	}
	
	/**
	 * 到货单列表数据(入库管理员，预知判断)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4rkStep1")
	@ResponseBody
	@RequestMapping(value = {"list4rkStep1"})
	public Map<String,Object> list4rkStep1(ArriveOrder arriveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArriveOrder> page = arriveOrderService.findPage(new Page<ArriveOrder>(request, response), arriveOrder, ArriveOrderService.DATA4RKSTEP1);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表(入库管理员，预知判断)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4rkStep1")
	@ResponseBody
	@RequestMapping(value = {"childList4rkStep1"})
	public Map<String,Object> childList4rkStep1(String pid,HttpServletRequest request, HttpServletResponse response) {
		ArriveOrderB entity = new ArriveOrderB();
		entity.setCarriveorderid(pid);
		List<ArriveOrderB> l = arriveOrderService.findChildList(new Page<ArriveOrderB>(request, response,-1),entity, ArriveOrderService.DATA4RKSTEP1);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 到货单列表(质检)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4zj")
	@RequestMapping(value = {"index4zj"})
	public String index4zj(Model model) {
		return "modules/supply/arriveOrderList4zj";
	}
	
	/**
	 * 到货单列表数据(质检)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4zj")
	@ResponseBody
	@RequestMapping(value = {"list4zj"})
	public Map<String,Object> list4zj(ArriveOrder arriveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArriveOrder> page = arriveOrderService.findPage(new Page<ArriveOrder>(request, response), arriveOrder, ArriveOrderService.DATA4ZJ);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表(质检)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4zj")
	@ResponseBody
	@RequestMapping(value = {"childList4zj"})
	public Map<String,Object> childList4zj(String pid,HttpServletRequest request, HttpServletResponse response) {
		ArriveOrderB entity = new ArriveOrderB();
		entity.setCarriveorderid(pid);
		List<ArriveOrderB> l = arriveOrderService.findChildList(new Page<ArriveOrderB>(request, response,-1),entity, ArriveOrderService.DATA4ZJ);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 到货单列表(入库管理员，生成入库单)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4rkStep2")
	@RequestMapping(value = {"index4rkStep2"})
	public String index4rkStep2(Model model) {
		return "modules/supply/arriveOrderList4rkStep2";
	}
	
	/**
	 * 到货单列表数据(入库管理员，生成入库单)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4rkStep2")
	@ResponseBody
	@RequestMapping(value = {"list4rkStep2"})
	public Map<String,Object> list4rkStep2(ArriveOrder arriveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArriveOrder> page = arriveOrderService.findPage(new Page<ArriveOrder>(request, response), arriveOrder, ArriveOrderService.DATA4RKSTEP2);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表(入库管理员，生成入库单)
	 * @author maliang
	 * @version 2015-11-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4rkStep2")
	@ResponseBody
	@RequestMapping(value = {"childList4rkStep2"})
	public Map<String,Object> childList4rkStep2(String pid,HttpServletRequest request, HttpServletResponse response) {
		ArriveOrderB entity = new ArriveOrderB();
		entity.setCarriveorderid(pid);
		List<ArriveOrderB> l = arriveOrderService.findChildList(new Page<ArriveOrderB>(request, response,-1),entity, ArriveOrderService.DATA4RKSTEP2);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 到货单列表(其他)
	 * @author maliang
	 * @version 2015-10-19
	 */
	@RequiresPermissions("supply:arriveOrder:view")
	@RequestMapping(value = {"index"})
	public String index(Model model) {
		model.addAttribute("pageAction", "other");
		return "modules/supply/arriveOrderList";
	}
	
	/**
	 * 到货单列表数据(其他)
	 * @author maliang
	 * @version 2015-10-17
	 */
	@RequiresPermissions("supply:arriveOrder:view")
	@ResponseBody
	@RequestMapping(value = {"list"})
	public Map<String,Object> list(ArriveOrder arriveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArriveOrder> page = arriveOrderService.findPage(new Page<ArriveOrder>(request, response), arriveOrder, ArriveOrderService.DATA4OTHER);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表(其他)
	 * @author maliang
	 * @version 2015-10-17
	 */
	@RequiresPermissions("supply:arriveOrder:view")
	@ResponseBody
	@RequestMapping(value = {"childList"})
	public Map<String,Object> childList(String pid,HttpServletRequest request, HttpServletResponse response) {
		ArriveOrderB entity = new ArriveOrderB();
		entity.setCarriveorderid(pid);
		List<ArriveOrderB> l = arriveOrderService.findChildList(new Page<ArriveOrderB>(request, response,-1),entity, ArriveOrderService.DATA4OTHER);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 到货单列表(展示)
	 * @author maliang
	 * @version 2016-03-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4show")
	@RequestMapping(value = {"index4show"})
	public String index4show() {
		return "modules/supply/arriveOrderList4show";
	}
	
	/**
	 * 到货单列表数据(展示)
	 * @author maliang
	 * @version 2016-03-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4show")
	@ResponseBody
	@RequestMapping(value = {"list4show"})
	public Map<String,Object> list4show(ArriveOrder arriveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArriveOrder> page = arriveOrderService.findPage(new Page<ArriveOrder>(request, response), arriveOrder, ArriveOrderService.DATA4SHOW);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}
	
	/**
	 * 到货单子表列表(展示)
	 * @author maliang
	 * @version 2016-03-04
	 */
	@RequiresPermissions("supply:arriveOrder:view4show")
	@ResponseBody
	@RequestMapping(value = {"childList4show"})
	public Map<String,Object> childList4show(String pid,HttpServletRequest request, HttpServletResponse response) {
		ArriveOrderB entity = new ArriveOrderB();
		entity.setCarriveorderid(pid);
		List<ArriveOrderB> l = arriveOrderService.findChildList(new Page<ArriveOrderB>(request, response,-1),entity, ArriveOrderService.DATA4SHOW);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 到货订单新增入库操作
	 * @author maliang
	 * @version 2015-10-13
	 */
//	@RequiresPermissions("supply:arriveOrder:makeArriveOrder")
//	@ResponseBody
//	@RequestMapping(value = "makeArriveOrder",method = RequestMethod.POST)
//	public Map<String, String> makeArriveOrder(ArriveOrder entity) {
//		Map<String, String> m = new HashMap<String, String>();
//		arriveOrderService.setErrmsg("数据异常！");
//		try {
//			boolean b = arriveOrderService.addData(entity);
//			if (b) {
//				m.put("status", "1");
//				m.put("msg", "到货订单生成成功");
//			}else{
//				m.put("status", "0");
//				m.put("msg", arriveOrderService.getErrmsg());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			m.put("status", "0");
//			m.put("msg", arriveOrderService.getErrmsg());
//		}
//		return m;
//	}
	
	/**
	 * 仓库管理员确认收货
	 * @author maliang
	 * @version 2015-10-22
	 */
	@RequiresPermissions("supply:arriveOrder:receive")
	@ResponseBody
	@RequestMapping(value = "receive",method = RequestMethod.POST)
	public Map<String, String> receive(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.saveData4Receive(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "操作成功");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 仓库管理员确认收货（取消操作）
	 * @author maliang
	 * @version 2016-01-21
	 */
	@RequiresPermissions("supply:arriveOrder:receiveCancel")
	@ResponseBody
	@RequestMapping(value = "receiveCancel",method = RequestMethod.POST)
	public Map<String, String> receiveCancel(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.saveData4ReceiveCancel(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "操作成功");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 到货订单质检
	 * @author maliang
	 * @version 2015-10-20
	 */
	@RequiresPermissions("supply:arriveOrder:checkQuality")
	@ResponseBody
	@RequestMapping(value = "checkQuality",method = RequestMethod.POST)
	public Map<String, String> checkQuality(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.saveData4Check(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "质检数据录入成功");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 质检签字
	 * @author maliang
	 * @version 2015-10-22
	 */
	@RequiresPermissions("supply:arriveOrder:zjSign")
	@ResponseBody
	@RequestMapping(value = "zjSign",method = RequestMethod.POST)
	public Map<String, String> zjSign(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.saveData4ZJSign(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "操作成功");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 质检签字（取消操作）
	 * @author maliang
	 * @version 2016-01-21
	 */
	@RequiresPermissions("supply:arriveOrder:zjSignCancel")
	@ResponseBody
	@RequestMapping(value = "zjSignCancel",method = RequestMethod.POST)
	public Map<String, String> zjSignCancel(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.saveData4ZJSignCancel(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "操作成功");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 入库管理员确认数量
	 * @author maliang
	 * @version 2015-10-22
	 */
	@RequiresPermissions("supply:arriveOrder:confirmNum")
	@ResponseBody
	@RequestMapping(value = "confirmNum",method = RequestMethod.POST)
	public Map<String, String> confirmNum(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.saveData4Confirm(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "确认数量完成");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 关闭单据
	 * @author maliang
	 * @version 2016-02-28
	 */
	@RequiresPermissions("supply:arriveOrder:closed")
	@ResponseBody
	@RequestMapping(value = "closed",method = RequestMethod.POST)
	public Map<String, String> closed(ArriveOrder entity) {
		Map<String, String> m = new HashMap<String, String>();
		arriveOrderService.setErrmsg("数据异常！");
		try {
			boolean b = arriveOrderService.closed(entity);
			if (b) {
				m.put("status", "1");
				m.put("msg", "操作成功！");
			}else{
				m.put("status", "0");
				m.put("msg", arriveOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", arriveOrderService.getErrmsg());
		}
		return m;
	}
	
	/**
	 * 删除单据
	 * @author maliang
	 * @version 2016-02-29
	 */
//	@RequiresPermissions("supply:arriveOrder:delete")
//	@ResponseBody
//	@RequestMapping(value = "delete",method = RequestMethod.POST)
//	public Map<String, String> delete(ArriveOrder entity) {
//		Map<String, String> m = new HashMap<String, String>();
//		arriveOrderService.setErrmsg("数据异常！");
//		try {
//			boolean b = arriveOrderService.delete(entity);
//			if (b) {
//				m.put("status", "1");
//				m.put("msg", "操作成功！");
//			}else{
//				m.put("status", "0");
//				m.put("msg", arriveOrderService.getErrmsg());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			m.put("status", "0");
//			m.put("msg", arriveOrderService.getErrmsg());
//		}
//		return m;
//	}
	
}
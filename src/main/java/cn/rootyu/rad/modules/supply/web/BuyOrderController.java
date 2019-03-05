/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.web;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.jqgridSearch.JqGridHandler;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.supply.entity.BuyOrder;
import cn.rootyu.rad.modules.supply.entity.BuyOrderB;
import cn.rootyu.rad.modules.supply.service.BuyOrderService;
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
 * 采购订单Controller
 * @author maliang
 * @version 2015-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/supply/buyOrder")
public class BuyOrderController extends BaseController {

	@Autowired
	private BuyOrderService buyOrderService;
	
	/**
	 * 采购订单列表
	 * @author maliang
	 * @version 2015-10-19
	 */
	@RequiresPermissions("supply:buyOrder:view")
	@RequestMapping(value = {"index", ""})
	public String index() {
		return "modules/supply/buyOrderList";
	}
	
	/**
	 * 采购订单列表数据
	 * @author maliang
	 * @version 2015-10-29
	 */
	@RequiresPermissions("supply:buyOrder:view")
	@ResponseBody
	@RequestMapping(value = {"list"})
	public Map<String,Object> list(BuyOrder buyOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (buyOrder == null) {
			buyOrder = new BuyOrder();
		}
		String where = new JqGridHandler(request).getWheres("bo", true);
		Map<String,String> sqlMap = new HashMap<String,String>();
		sqlMap.put("fwhere", where);
		buyOrder.setSqlMap(sqlMap);
		Page<BuyOrder> page = buyOrderService.findPage(new Page<BuyOrder>(request, response), buyOrder);
		Map<String,Object> m = new HashMap<String,Object>();
        m.put("total", page.getTotalPage());
        m.put("pageNo", page.getPageNo());
        m.put("records", page.getCount());
        m.put("rows", page.getList());
        return m;
	}

	/**
	 * 采购订单子表列表
	 * @author maliang
	 * @version 2015-10-19
	 */
	@RequiresPermissions("supply:buyOrder:view")
	@ResponseBody
	@RequestMapping(value = {"childList"})
	public Map<String,Object> childList(String pid) {
		BuyOrderB entity = new BuyOrderB();
		entity.setCorderid(pid);
		List<BuyOrderB> l = buyOrderService.findChildList(entity);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rows", l);
        return m;
	}
	
	/**
	 * 重置采购订单
	 * @author maliang
	 * @version 2016-01-17
	 */
	@ResponseBody
	@RequestMapping(value = {"reset"})
	public Map<String,String> reset(BuyOrder buyOrder) {
		Map<String, String> m = new HashMap<String, String>();
		buyOrderService.setErrmsg("数据异常！");
		try {
			boolean b = buyOrderService.reset(buyOrder);
			if (b) {
				m.put("status", "1");
				m.put("msg", "重置成功");
			}else{
				m.put("status", "0");
				m.put("msg", buyOrderService.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("status", "0");
			m.put("msg", buyOrderService.getErrmsg());
		}
        return m;
	}

}
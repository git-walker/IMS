/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.service;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.BaseService;
import cn.rootyu.rad.common.utils.ConstantUtils;
import cn.rootyu.rad.modules.supply.dao.BuyOrderDao;
import cn.rootyu.rad.modules.supply.entity.*;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购单Service
 * @author maliang
 * @version 2015-10-19
 */
@Service
@Transactional(readOnly = true)
public class BuyOrderService extends BaseService {

	@Autowired
	private BuyOrderDao buyOrderDao;
	
	//错误信息
	private String errmsg;
		
	/**
	 * 分页查询采购单主表
	 * @author maliang
	 * @version 2015-10-19
	 * update 2015-10-22
	 */
	public Page<BuyOrder> findPage(Page<BuyOrder> page, BuyOrder entity) {
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			page.setList(null);
			return page;
		}
		if (entity == null) {
			entity = new BuyOrder();
		}
		BuyOrder boWhere = new BuyOrder();
		boWhere.setForderstatus(ConstantUtils.BuyOrder.ORDER_NORMAL);
		if (!(entity.getIisactive() == null || entity.getIisactive().equals(""))) {
			boWhere.setIisactive(entity.getIisactive());
		}
		//查询条件
		boWhere.setSqlMap(entity.getSqlMap());
//		boWhere.setCemployeeid(userCur.getId());// 采购员ID
		boWhere.setDelFlag(BuyOrder.DEL_FLAG_NORMAL);
		boWhere.setPage(page);
		CuManDoc cmd = new CuManDoc();
		cmd.setDelFlag(CuManDoc.DEL_FLAG_NORMAL);
		boWhere.setCmd(cmd);
		CuBasDoc cbd = new CuBasDoc();
		if (entity.getCbd() != null) {
			if (!(entity.getCbd().getCustname() == null || entity.getCbd().getCustname().equals(""))) {
				cbd.setCustname(entity.getCbd().getCustname());
			}
		}
		cbd.setDelFlag(CuBasDoc.DEL_FLAG_NORMAL);
		boWhere.setCbd(cbd);
		User cgemp = new User();
		cgemp.setDelFlag(User.DEL_FLAG_NORMAL);
		boWhere.setCgemp(cgemp);
		User zdemp = new User();
		zdemp.setDelFlag(User.DEL_FLAG_NORMAL);
		boWhere.setZdemp(zdemp);
		List<BuyOrder> boList = buyOrderDao.findList(boWhere);
		page.setList(boList);
		return page;
	}
	
	/**
	 * 查询采购单子表
	 * @author maliang
	 * @version 2015-10-19
	 * update 2015-10-22
	 */
	public List<BuyOrderB> findChildList(BuyOrderB entity) {
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return null;
		}
		BuyOrderB bobWhere = new BuyOrderB();
		if (entity == null) {
			return null;
		}
		if (entity.getCorderid() == null || entity.getCorderid().equals("")) {
			return null;
		}
		//判断当前登录用户的权限(是否可以看此采购订单下的存货)
		BuyOrder boWhere = new BuyOrder();
		boWhere.setId(entity.getCorderid());
//		boWhere.setCemployeeid(userCur.getId());
		boWhere.setDelFlag(BuyOrder.DEL_FLAG_NORMAL);
		BuyOrder boInfo = buyOrderDao.findInfo(boWhere);
		if (boInfo == null) {
			return null;
		}
		bobWhere.setCorderid(entity.getCorderid());
		bobWhere.setDelFlag(BuyOrderB.DEL_FLAG_NORMAL);
		InvBasDoc ibdWhere = new InvBasDoc();
		ibdWhere.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
		MeasDoc md = new MeasDoc();
		md.setDelFlag(MeasDoc.DEL_FLAG_NORMAL);
		ibdWhere.setMd(md);
		bobWhere.setIbd(ibdWhere);
		StorDoc sd = new StorDoc();
		sd.setDelFlag(StorDoc.DEL_FLAG_NORMAL);
		bobWhere.setSd(sd);
		return buyOrderDao.findChildList(bobWhere);
	}
	
	/**
	 * 重置采购订单
	 * @author maliang
	 * @version 2016-01-17
	 */
	public boolean reset(BuyOrder entity){
		boolean b = false;
		if (entity.getVordercode() == null || entity.getVordercode().equals("")) {
			this.errmsg = "采购单不存在";
			return b;
		}
		BuyOrder boWhere = new BuyOrder();
		boWhere.setVordercode(entity.getVordercode());
		boWhere.setDelFlag(BuyOrder.DEL_FLAG_NORMAL);
		BuyOrder boInfo = buyOrderDao.findInfo(boWhere);
		if (boInfo == null) {
			this.errmsg = "采购单不存在";
			return b;
		}
		BuyOrderB bobWhere = new BuyOrderB();
		bobWhere.setCorderid(boInfo.getId());
		bobWhere.setDelFlag(BuyOrderB.DEL_FLAG_NORMAL);
		List<BuyOrderB> bobList = buyOrderDao.findChildList(bobWhere);
		if (bobList == null || bobList.size() < 1) {
			this.errmsg = "采购单异常";
			return b;
		}
		for (BuyOrderB bobItem : bobList) {
			bobItem.setIisactive(ConstantUtils.BuyOrderB.ORDER_NORMAL);
			bobItem.setNaccumarrvnum(0.0);
			bobItem.setNaccumstorenum(0.0);
			buyOrderDao.updateChild(bobItem);
		}
		b = true;
		return b;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
		
}
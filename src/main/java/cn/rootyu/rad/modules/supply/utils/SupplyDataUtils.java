package cn.rootyu.rad.modules.supply.utils;

import cn.rootyu.rad.common.utils.ConstantUtils;
import cn.rootyu.rad.common.utils.SpringContextHolder;
import cn.rootyu.rad.modules.supply.dao.SupplyDataDao;
import cn.rootyu.rad.modules.supply.entity.*;
import cn.rootyu.rad.modules.sys.entity.User;

import java.util.*;

/**
 * 来料管理所需数据工具类
 * @author maliang
 * @version 2015-11-16
 */
public class SupplyDataUtils {
	
	private static SupplyDataDao dao = SpringContextHolder.getBean(SupplyDataDao.class);
	
	/**
	 * 获取物料、货位、工位、项目仓库的关系（包含工艺路线）
	 * @author maliang
	 * @version 2015-11-09
	 */
	public static List<InvRelation> findInvRelationList(InvRelation where){
		return dao.findInvRelationList(where);
	}
	
	/**
	 * 获取物料、货位、工位、项目仓库的关系
	 * @author maliang
	 * @version 2016-03-05
	 */
	public static List<InvRelation> findInvRelationNoRtList(InvRelation where){
		return dao.findInvRelationNoRtList(where);
	}
	
//	/**
//	 * 批量获取物料数据
//	 * @author maliang
//	 * @version 2015-11-09
//	 */
//	public static Map<String,List<InvRelation>> findInvRelationMap(String key,InvRelation where){
//		boolean keyIsJmfStorInv = false;
//		if (key.equals("jmf_stor_inv")) {
//			keyIsJmfStorInv = true;
//		}
//		Map<String,List<InvRelation>> map = new HashMap<String,List<InvRelation>>();
//		List<InvRelation> list = findInvRelationList(where);
//		for (InvRelation item : list) {
//			if (keyIsJmfStorInv) {
//				StringBuffer strTmp = new StringBuffer();
//				strTmp.append(item.getPkJobmngfil());
//				strTmp.append(item.getSmf().getPkCwarehouseid());
//				strTmp.append(item.getPkInvbasdoc());
//				String keyVal = strTmp.toString();
//				if (!map.containsKey(keyVal)) {
//					map.put(keyVal, new ArrayList<InvRelation>());
//				}
//				map.get(keyVal).add(item);
//			}
//		}
//		return map;
//	}
	
	/**
	 * 获取现存量数据
	 * @author maliang
	 * @version 2015-11-27
	 */
	public static List<OnhandNum> findOnhandNumList(OnhandNum where){
		return dao.findOnhandNumList(where);
	}
	
	/**
	 * 批量获取物料数据
	 * @author maliang
	 * @version 2015-11-09
	 */
	public static Map<String,List<OnhandNum>> findOnhandNumMap(String key, OnhandNum where){
		boolean keyIsStorInv = false;
		if (key.equals("stor_inv")) {
			keyIsStorInv = true;
		}
		Map<String,List<OnhandNum>> map = new HashMap<String,List<OnhandNum>>();
		List<OnhandNum> list = findOnhandNumList(where);
		for (OnhandNum item : list) {
			if (keyIsStorInv) {
				String keyVal = item.getPkStordoc() + item.getPkInvbasdoc();
				if (!map.containsKey(keyVal)) {
					map.put(keyVal, new ArrayList<OnhandNum>());
				}
				map.get(keyVal).add(item);
			}
		}
		return map;
	}
	
	/**
	 * 获取工位数据
	 * @author maliang
	 * @version 2016-3-2
	 */
	public static Station findStationInfo(Station where){
		Station info = dao.findStationInfo(where);
		return info;
	}
	
	/**
	 * 批量获取工位数据
	 * @author maliang
	 * @version 2015-12-2
	 */
	public static List<Station> findStationList(Station where){
		List<Station> l = dao.findStationList(where);
		return l;
	}
	
	/**
	 * 批量获取工位数据
	 * @author maliang
	 * @version 2015-12-2
	 */
	public static Map<String, Station> findStationMap(String key, Station where){
		boolean keyIsId = false;
		if (key.equals("id")) {
			keyIsId = true;
		}
		Map<String, Station> m = new HashMap<String, Station>();
		List<Station> l = findStationList(where);
		for (Station item : l) {
			if (keyIsId && !m.containsKey(item.getId())) {
				m.put(item.getId(), item);
			}
		}
		return m;
	}
	
	/**
	 * 批量获取货位数据
	 * @author maliang
	 * @version 2016-3-2
	 */
	public static CargDoc findCargDocInfo(CargDoc where){
		CargDoc info = dao.findCargDocInfo(where);
		return info;
	}
	
	/**
	 * 批量获取货位数据
	 * @author maliang
	 * @version 2015-12-3
	 */
	public static List<CargDoc> findCargDocList(CargDoc where){
		List<CargDoc> l = dao.findCargDocList(where);
		return l;
	}
	
	/**
	 * 批量获取货位数据
	 * @author maliang
	 * @version 2015-12-3
	 */
	public static Map<String, CargDoc> findCargDocMap(String key, CargDoc where){
		boolean keyIsId = false;
		if (key.equals("id")) {
			keyIsId = true;
		}
		Map<String, CargDoc> m = new HashMap<String, CargDoc>();
		List<CargDoc> l = findCargDocList(where);
		for (CargDoc item : l) {
			if (keyIsId && !m.containsKey(item.getId())) {
				m.put(item.getId(), item);
			}
		}
		return m;
	}
	
	/**
	 * 查询项目仓库数据
	 * @author maliang
	 * @version 2016-01-09
	 */
	public static StorDoc findStorDocInfo(StorDoc where){
		StorDoc info = dao.findStorDocInfo(where);
		return info;
	}
	
	/**
	 * 批量获取项目仓库数据
	 * @author maliang
	 * @version 2015-11-12
	 */
	public static List<StorDoc> findStorDocList(StorDoc where){
		List<StorDoc> l = dao.findStorDocList(where);
		return l;
	}
	
	/**
	 * 批量获取项目仓库数据
	 * @author maliang
	 * @version 2015-12-2
	 */
	public static Map<String, StorDoc> findStorDocMap(String key, StorDoc where){
		boolean keyIsId = false;
		if (key.equals("id")) {
			keyIsId = true;
		}
		Map<String, StorDoc> m = new HashMap<String, StorDoc>();
		List<StorDoc> l = findStorDocList(where);
		for (StorDoc item : l) {
			if (keyIsId && !m.containsKey(item.getId())) {
				m.put(item.getId(), item);
			}
		}
		return m;
	}
	
	/**
	 * 查询用户信息
	 * @author maliang
	 * @version 2015-11-13
	 */
	public static User findUserInfo(User where){
		User info = dao.findUserInfo(where);
		return info;
	}
	
	/**
	 * 查询用户信息
	 * @author maliang
	 * @version 2015-11-13
	 */
	public static List<User> findUserList(User where){
		List<User> l = dao.findUserList(where);
		return l;
	}
	
	/**
	 * 获取物料数据
	 * @author maliang
	 * @version 2016-01-25
	 */
	public static InvBasDoc findInvBasDocInfo(InvBasDoc where){
		InvBasDoc info = dao.findInvBasDocInfo(where);
		return info;
	}
	
	/**
	 * 批量获取项目数据
	 * @author maliang
	 * @version 2015-12-1
	 */
	public static JobBasFil findJobBasFilInfo(JobBasFil where){
		JobBasFil info = dao.findJobBasFilInfo(where);
		return info;
	}
	
	/**
	 * 到货单各种异常
	 * @author maliang
	 * @version 2016-03-06
	 */
	public static String getEstatus(ArriveOrder aoInfo, Date nowTime){
		String status = ConstantUtils.ArriveOrder.E_NORMAL;
		Calendar c = Calendar.getInstance();
		if (ConstantUtils.ArriveOrder.RECEIVE_NOT.equals(aoInfo.getReceiveflag())) {
			c.setTime(aoInfo.getCreateDate());
			c.add(Calendar.HOUR_OF_DAY, 6);
			//预知判断超时
			if (c.getTime().before(nowTime)) {
				return ConstantUtils.ArriveOrder.E_YZPD;
			}
		}else if (ConstantUtils.ArriveOrder.SIGN_NOT.equals(aoInfo.getZjsignState())) {
			c.setTime(aoInfo.getInfQcDate());
			c.add(Calendar.HOUR_OF_DAY, 4);
			//质检超时
			if (c.getTime().before(nowTime)) {
				return ConstantUtils.ArriveOrder.E_ZJ;
			}
		}else if (ConstantUtils.ArriveOrder.SIGN_NOT.equals(aoInfo.getRksignState())) {
			c.setTime(aoInfo.getZjdate());
			c.add(Calendar.HOUR_OF_DAY, 6);
			//入账超时
			if (c.getTime().before(nowTime)) {
				return ConstantUtils.ArriveOrder.E_RZ;
			}
		}else if (ConstantUtils.ArriveOrder.CLOSED_NOT.equals(aoInfo.getHclosed())) {
			c.setTime(aoInfo.getRkdate());
			c.add(Calendar.DAY_OF_MONTH, 3);
			//入库超时
			if (c.getTime().before(nowTime)) {
				return ConstantUtils.ArriveOrder.E_RK;
			}
		}
		return status;
	}

}

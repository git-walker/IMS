package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.Invcl;


/**
 * 存货分类DAO接口
 * @author xiujun.xu
 * @version 2015-10-28
 */
@MyBatisDao
public interface InvclDao extends CrudDao<Invcl> {

}

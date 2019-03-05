package cn.rootyu.ims.tools.dao;

import cn.rootyu.ims.tools.entity.SelectPage;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * @InterfaceName SelectPageDao
 * @Description TODO
 * @Authour qinyi
 * @Date 2018/8/27 14:29
 * @Version 1.0
 */
@MyBatisDao
public interface SelectPageDao {

    List<SelectPage> findUserPage(SelectPage selectPage);
    List<SelectPage> findCompanyLeaderUserPage(SelectPage selectPage);

    List<SelectPage> findOfficePage(SelectPage selectPage);

    List<SelectPage> findResponUnitPage(SelectPage selectPage);

    List<SelectPage> findAttendResponUnitPage(SelectPage selectPage);

	List<SelectPage> findOfficeUserPage(SelectPage selectPage);
}

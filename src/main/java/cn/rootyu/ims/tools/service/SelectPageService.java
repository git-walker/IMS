package cn.rootyu.ims.tools.service;

import cn.rootyu.ims.tools.dao.SelectPageDao;
import cn.rootyu.ims.tools.entity.SelectPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName SelectPageService
 * @Description TODO
 * @Authour qinyi
 * @Date 2018/8/27 14:29
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SelectPageService {

    @Autowired
    private SelectPageDao selectPageDao;

    public List<SelectPage> findCompanyLeaderUserPage(SelectPage selectPage, String queNature) {
        if ("10".equals(queNature) || "true".equals(queNature)){
            return selectPageDao.findCompanyLeaderUserPage(selectPage);
        }
        return selectPageDao.findUserPage(selectPage);
    }
    public List<SelectPage> findUserPage(SelectPage selectPage) {
        return selectPageDao.findUserPage(selectPage);
    }
    public List<SelectPage> findOfficeUserPage(SelectPage selectPage) {
        return selectPageDao.findOfficeUserPage(selectPage);
    }
    public List<SelectPage> findResponUnitPage(SelectPage selectPage) {
        return selectPageDao.findResponUnitPage(selectPage);
    }

    public List<SelectPage> findAttendResponUnitPage(SelectPage selectPage) {
        return selectPageDao.findAttendResponUnitPage(selectPage);
    }


}

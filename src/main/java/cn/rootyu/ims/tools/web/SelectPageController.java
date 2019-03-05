package cn.rootyu.ims.tools.web;

import cn.rootyu.ims.tools.entity.SelectPage;
import cn.rootyu.ims.tools.service.SelectPageService;
import cn.rootyu.rad.common.persistence.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SelectPageController
 * @Description SELECT2 分页工具 Controller
 * @Authour qinyi
 * @Date 2018/8/27 14:26
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/qmis/tools/selectPage")
public class SelectPageController {

    @Autowired
    private SelectPageService selectPageService;


    /**
     * @return
     * @Description 分页查询人员列表
     * @Author qinyi
     * @Date 2018/8/27 14:49
     * @Param
     **/
    @RequestMapping(value = {"findUserPage"})
    @ResponseBody
    public Map<String, Object> findUserPage(SelectPage selectPage) {
        Map<String, Object> returnMap = new HashMap<>(4);
        List<SelectPage> selectPages = selectPageService.findUserPage(selectPage);
        Page<SelectPage> pagePage = selectPage.getPage();
        returnMap.put("total", pagePage.getCount() / pagePage.getPageSize() + 1);
        returnMap.put("pageNo", pagePage.getPageNo());
        returnMap.put("records", pagePage.getCount());
        returnMap.put("rows", selectPages);
        return returnMap;
    }

    @RequestMapping(value = {"findCompanyLeaderUserPage"})
    @ResponseBody
    public Map<String, Object> findCompanyLeaderUserPage(SelectPage selectPage,String queNature) {
        Map<String, Object> returnMap = new HashMap<>(4);
        List<SelectPage> selectPages = selectPageService.findCompanyLeaderUserPage(selectPage,queNature);
        Page<SelectPage> pagePage = selectPage.getPage();
        returnMap.put("total", pagePage.getCount() / pagePage.getPageSize() + 1);
        returnMap.put("pageNo", pagePage.getPageNo());
        returnMap.put("records", pagePage.getCount());
        returnMap.put("rows", selectPages);
        return returnMap;
    }

    @RequestMapping(value = {"findOfficeUserPage"})
    @ResponseBody
    public Map<String, Object> findOfficeUserPage(SelectPage selectPage,String practiceUnitId) {
        Map<String, Object> returnMap = new HashMap<>(4);
        selectPage.setParam(practiceUnitId);
        List<SelectPage> selectPages = selectPageService.findOfficeUserPage(selectPage);
        Page<SelectPage> pagePage = selectPage.getPage();
        returnMap.put("total", pagePage.getCount() / pagePage.getPageSize() + 1);
        returnMap.put("pageNo", pagePage.getPageNo());
        returnMap.put("records", pagePage.getCount());
        returnMap.put("rows", selectPages);
        return returnMap;
    }
    /**
     * @return
     * @Description 分页获取责任单位列表 若param 参数存在则指定内外部单位，否则不予返回
     * @Author qinyi
     * @Date 2018/8/28 10:55
     * @Param
     **/
    @RequestMapping(value = {"findResponUnitPage"})
    @ResponseBody
    public Map<String, Object> findResponUnitPage(SelectPage selectPage) {
        Map<String, Object> returnMap = new HashMap<>(4);
        if (selectPage == null) {
            SelectPage errorPage = new SelectPage();
            errorPage.setText("分页异常！");
            returnMap.put("rows", errorPage);
            return returnMap;
        }
        List<SelectPage> selectPages = selectPageService.findResponUnitPage(selectPage);
        Page<SelectPage> pagePage = selectPage.getPage();
        returnMap.put("total", pagePage.getCount() / pagePage.getPageSize() + 1);
        returnMap.put("pageNo", pagePage.getPageNo());
        returnMap.put("records", pagePage.getCount());
        returnMap.put("rows", selectPages);
        return returnMap;
    }


    @RequestMapping(value = {"findAttendResponUnitPage"})
    @ResponseBody
    public Map<String, Object> findAttendResponUnitPage(SelectPage selectPage) {
        Map<String, Object> returnMap = new HashMap<>(4);
        if (selectPage == null) {
            SelectPage errorPage = new SelectPage();
            errorPage.setText("分页有误！");
            returnMap.put("rows", errorPage);
            return returnMap;
        }
        List<SelectPage> selectPages = selectPageService.findAttendResponUnitPage(selectPage);
        Page<SelectPage> pagePage = selectPage.getPage();
        returnMap.put("total", pagePage.getCount() / pagePage.getPageSize() + 1);
        returnMap.put("pageNo", pagePage.getPageNo());
        returnMap.put("records", pagePage.getCount());
        returnMap.put("rows", selectPages);
        return returnMap;
    }


}

package cn.rootyu.ims.basisData.web;
/**
 * 售后供应商维护
 */

import cn.rootyu.ims.basisData.entity.QmisProvide;
import cn.rootyu.ims.basisData.service.QmisProvideService;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.ims.common.dao.CodeValueDao;
import cn.rootyu.ims.common.entity.CodeValue;
import cn.rootyu.rad.common.utils.IdGen;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/qmis/provide")
public class QmisProvideController extends BaseController {

    @Autowired
    CodeValueDao codeValueDao;
    @Autowired
    QmisProvideService provideService;

    @RequestMapping(value = {"list", ""})
    public String list(QmisProvide Provide , HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CodeValue> nameList = codeValueDao.findTypeList();
        model.addAttribute("nameList", nameList);
        return "qmis/basisData/provideList";
    }


    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(QmisProvide Provide, HttpServletRequest request, HttpServletResponse response) {
        Page<QmisProvide> page = provideService.findPage(new Page<QmisProvide>(request, response), Provide);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }


    @RequestMapping(value = "form")
    public String form(String id, Model model) {
        QmisProvide provide = new QmisProvide();
        if(StringUtils.isNotBlank(id)){
            provide = provideService.get(id);
        }
        model.addAttribute("provide",provide);
        return "qmis/basisData/provideForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(QmisProvide provide, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (StringUtils.isBlank(provide.getId())) {
            provide.setId(IdGen.uuid());
            provide.setDelFlag("0");
            provideService.insert(provide);
        }else{
            provideService.updata(provide);
        }
        addMessageAjax(returnMap, "1", "保存成功");
        return returnMap;
    }

    @RequestMapping(value = "batchDelete")
    @ResponseBody
    public Map<String, Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        provideService.batchDelete(ids);
        addMessageAjax(returnMap, "1", "删除成功");
        return returnMap;
    }
    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, Object> delete(String id, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        QmisProvide provide = new QmisProvide();
        provide.setId(id);
        provideService.delete(provide);
        addMessageAjax(returnMap, "1", "删除成功");
        return returnMap;
    }
}

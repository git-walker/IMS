package cn.rootyu.ims.purchase.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.purchase.entity.SupplierDir;
import cn.rootyu.ims.purchase.entity.SupplierDirDetail;
import cn.rootyu.ims.purchase.service.SupplierDirService;
import cn.rootyu.rad.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SupplierDirController
 * @Description 合格供方名录维护
 * @Author yuhui
 * @Date 2019/4/14 16:32
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/purchase/supplierDir")
public class SupplierDirController extends BaseController {

    @Autowired
    private SupplierDirService supplierDirService;

    /**
     * 导航到供方名录页面
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "ims/purchase/supplier/supplierDirList";
    }

    /**
     * 导航到供方名录添加页面
     * @param request
     * @return
     */
    @RequestMapping(value = "initSupplierDirAdd")
    public String initAddPlan(HttpServletRequest request) {
        LocalDate next=LocalDate.now();
        List<SupplierDir> yearList=new ArrayList<>();
        for (int i=next.getYear();i>=next.getYear()-5;i--){
            yearList.add(new SupplierDir(i));
        }
        request.setAttribute("yearList",yearList);
        return "ims/purchase/supplier/supplierDirAdd";
    }

    /**
     * 导航到供方名录编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "initSupplierDirEdit")
    public String initSupplierEdit(String id,HttpServletRequest request) {
        SupplierDir supplierDir = supplierDirService.getSupplierDir(id);
        request.setAttribute("dir",supplierDir);
        return "ims/purchase/supplier/supplierDirEdit";
    }

    /**
     * 导航到供方名录合格供方列表查看页面
     * @param dirId
     * @param request
     * @return
     */
    @RequestMapping(value = "initQualifiedSupplierList")
    public String initQualifiedSupplierList(String dirId, HttpServletRequest request) {

        SupplierDir supplierDir = supplierDirService.getSupplierDir(dirId);
        request.setAttribute("dir",supplierDir);
        return "ims/purchase/supplier/qualifiedSupplierDetail";
    }

    /**
     * 航到供方名录合格供方列表编辑页面
     * @param dirId
     * @param request
     * @return
     */
    @RequestMapping(value = "initQualifiedSupplierEdit")
    public String initQualifiedSupplierEdit(String dirId, HttpServletRequest request) {
        request.setAttribute("dirId",dirId);
        SupplierDir supplierDir = supplierDirService.getSupplierDir(dirId);
        request.setAttribute("dir",supplierDir);
        return "ims/purchase/supplier/qualifiedSupplierEdit";
    }
    /**
     * 供方名录列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierDirList")
    @ResponseBody
    public LayuiPageInfo supplierList(@RequestBody Map<String,Object> params) throws Exception{
        LayuiPageInfo<SupplierDir> pageInfo=null;
        try{
            pageInfo= supplierDirService.getSupplierDirPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }


    /**
     * 合格的名单列表
     * @param dirId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierDirDetailPageList")
    @ResponseBody
    public LayuiPageInfo supplierList(String dirId) throws Exception{
        LayuiPageInfo<SupplierDirDetail> pageInfo=null;
        try{
            Map<String,Object> params=new HashMap<>();
            params.put("dirId",dirId);
            pageInfo= supplierDirService.getSupplierDirDetailPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    /**
     * 待选的供应商列表
     * @param dirId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "unCheckedSupplierPageList")
    @ResponseBody
    public LayuiPageInfo unCheckedSupplierPageList(String dirId) throws Exception{
        LayuiPageInfo<SupplierDirDetail> pageInfo=null;
        try{
            Map<String,Object> params=new HashMap<>();
            params.put("dirId",dirId);
            pageInfo= supplierDirService.unCheckedSupplierPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }
    /**
     * 添加名录
     * @param dir
     * @return
     */
    @RequestMapping(value = "supplierDirAdd")
    @ResponseBody
    public ResultBean supplierDirAdd(SupplierDir dir){
        ResultBean resultBean=new ResultBean();
        try{
            supplierDirService.addSupplierDir(dir);
            resultBean.setData(dir.getId());
        }catch (DuplicateKeyException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("记录已存在");
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }


    /**
     * 修改供方名录
     * @param supplierDir
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierDirUpdate")
    @ResponseBody
    public ResultBean supplierDirUpdate(SupplierDir supplierDir) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(supplierDir.getId(),"更新主键不能为空");
            supplierDirService.updateSupplierDir(supplierDir);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    /**
     * 删除供方名录
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierDirDel")
    @ResponseBody
    public ResultBean supplierDirDel(String id) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            supplierDirService.supplierDirDel(idArray);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    /**
     * 勾选合格供方，取消合格供方
     * @param id
     * @param check 为true时表示勾选为合格供方
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "checkQualifiedSupplier")
    @ResponseBody
    public ResultBean checkQualifiedSupplier(String id,String supplierDirId,String check) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"未勾选任务记录");
            Assert.hasText(supplierDirId,"名录ID不能为空");
            String idArray[]=id.split(",");
            boolean isCheck="true".equals(check);
            supplierDirService.checkQualifiedSupplier(idArray,supplierDirId, isCheck);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }
    /**
     * 查询某供应商的历年合格供方情况
     * @param supplierId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierHistoryDirPageList")
    @ResponseBody
    public LayuiPageInfo supplierHistoryDirPageList(String supplierId) throws Exception{
        LayuiPageInfo<SupplierDirDetail> pageInfo=null;
        try{
            Assert.hasText(supplierId,"供应商ID不能为空");
            Map<String,Object> params=new HashMap<>();
            params.put("supplierId",supplierId);
            pageInfo= supplierDirService.supplierHistoryDirPageList(params);
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage(),e);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }
}

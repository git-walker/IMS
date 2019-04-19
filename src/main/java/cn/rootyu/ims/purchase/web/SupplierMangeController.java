package cn.rootyu.ims.purchase.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.purchase.entity.Supplier;
import cn.rootyu.ims.purchase.service.SupplierManageService;
import cn.rootyu.rad.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName SupplierMangeController
 * @Description 采购管理-供应商管理
 * @Author yuhui
 * @Date 2019/4/14 15:16
 * @Version 1.0
 */

@Controller
@RequestMapping(value = "${adminPath}/ims/purchase/supplier")
public class SupplierMangeController extends BaseController {

    @Autowired
    private SupplierManageService supplierManageService;

    /**
     * 供应商列表
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "ims/purchase/supplier/supplierList";
    }

    /**
     * 导航到供应商添加页面
     *
     */
    @RequestMapping(value = "initSupplierAdd")
    public String initSupplierAdd() {
        return "ims/purchase/supplier/supplierAdd";
    }

    /**
     * 导航到供应商编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "initSupplierEdit")
    public String initSupplierEdit(String id,String readonly,HttpServletRequest request) {
        Supplier supplier = supplierManageService.getSupplier(id);
        request.setAttribute("supplier",supplier);
        if ("true".equals(readonly)){
            request.setAttribute("readonly",true);
        }
        return "ims/purchase/supplier/supplierEdit";
    }

    /**
     * 列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierList")
    @ResponseBody
    public LayuiPageInfo supplierList(@RequestBody Map<String,Object> params) throws Exception{
        LayuiPageInfo<Supplier> pageInfo=null;
        try{
            pageInfo=supplierManageService.getSupplierPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    /**
     * 新增供应商
     * @param supplier
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierAdd")
    @ResponseBody
    public ResultBean addSupplier(Supplier supplier) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            supplierManageService.addSupplier(supplier);
        }catch (DuplicateKeyException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("年度名录已存在");
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
     * 更新供应商
     * @param supplier
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "supplierUpdate")
    @ResponseBody
    public ResultBean updateSupplier(Supplier supplier) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(supplier.getId(),"更新主键不能为空");
            supplierManageService.updateSupplier(supplier);
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
     * 删除供应商
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "suppliersDelete")
    @ResponseBody
    public ResultBean delSuppliers(String id) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            supplierManageService.delSuppliers(idArray);
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
}

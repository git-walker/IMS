package cn.rootyu.ims.resourceManage.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.purchase.entity.Supplier;
import cn.rootyu.ims.purchase.service.SupplierManageService;
import cn.rootyu.ims.resourceManage.entity.Goods;
import cn.rootyu.ims.resourceManage.service.GoodsService;
import cn.rootyu.rad.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoodsController
 * @Description 商品Controller
 * @Author yuhui
 * @Date 2019/4/13 14:12
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/resourceManage/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SupplierManageService supplierManageService;

    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Supplier> supplierList = supplierManageService.getAllSupplier();//所有供应商
        model.addAttribute("supplierList",supplierList);
        return "ims/resourceManage/goods/goodsList";
    }

    /**
     * 列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "goodsList")
    @ResponseBody
    public LayuiPageInfo goodsList(@RequestBody Map<String,Object> params){
        LayuiPageInfo<Goods> pageInfo=null;
        try{
            pageInfo=goodsService.getGoodsPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    @RequestMapping(value = "initAddGoods")
    public String initAddGoods(Model model) {
        List<Supplier> supplierList = supplierManageService.getAllSupplier();//所有供应商
        model.addAttribute("supplierList",supplierList);
        return "ims/resourceManage/goods/goodsAdd";
    }

    @RequestMapping(value = "initEditGoods")
    public String initEditGoods(String id, String readonly, HttpServletRequest request, Model model) {
        Goods goods = goodsService.getGoods(id);
        request.setAttribute("goods",goods);
        List<Supplier> supplierList = supplierManageService.getAllSupplier();//所有供应商
        model.addAttribute("supplierList",supplierList);
        if ("true".equals(readonly)){
            request.setAttribute("readonly",true);
        }
        return "ims/resourceManage/goods/goodsEdit";
    }

    /**
     * 新增商品信息
     * @param goods
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addGoods")
    @ResponseBody
    public ResultBean addGoods(Goods goods,List<MultipartFile> imgAttach,String imgAttachDesc){
        ResultBean resultBean=new ResultBean();
        try{
            goodsService.addGoods(goods,imgAttach,imgAttachDesc);
            resultBean.setData(goods.getId());
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
     * 修改商品信息
     * @param goods
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateGoods")
    @ResponseBody
    public ResultBean updateGoods(Goods goods){
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(goods.getId(),"更新主键不能为空");
            goodsService.updateGoods(goods);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setMsg(e.getMessage());
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    /**
     * 删除设备信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delGoods")
    @ResponseBody
    public ResultBean delGoods(String id){
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            goodsService.removeGoods(idArray);//逻辑删除
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

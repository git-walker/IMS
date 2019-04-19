package cn.rootyu.ims.dataManage.web;

import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.dataManage.entity.StockInfo;
import cn.rootyu.ims.dataManage.service.StockInfoService;
import cn.rootyu.ims.resourceManage.entity.Repo;
import cn.rootyu.ims.resourceManage.service.RepoService;
import cn.rootyu.rad.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName StockInfoController
 * @Description 库存详情Controller
 * @Author yuhui
 * @Date 2019/4/19 15:22
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/dataManage/stock")
public class StockInfoController extends BaseController {

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private RepoService repoService;

    @RequestMapping(value = {"list", ""})
    public String list(Model model){
        List<Repo> repoList = repoService.getAllRepo();//仓库
        model.addAttribute("repoList",repoList);
        return "ims/dataManage/stockInfo";
    }

    @RequestMapping(value = "info")
    @ResponseBody
    public ResultBean info(String repoId){
        ResultBean resultBean=new ResultBean();
        try {
            Assert.hasText(repoId, "请选择仓库");
            List<StockInfo> mapList = stockInfoService.queryStockInfo(repoId);
            resultBean.setData(mapList);
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
}

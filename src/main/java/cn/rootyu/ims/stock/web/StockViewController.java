package cn.rootyu.ims.stock.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.resourceManage.entity.Repo;
import cn.rootyu.ims.resourceManage.service.RepoService;
import cn.rootyu.ims.stock.entity.Stock;
import cn.rootyu.ims.stock.service.StockManageService;
import cn.rootyu.rad.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName StockViewController
 * @Description 库存查看Controller
 * @Author yuhui
 * @Date 2019/4/19 12:41
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/stockManage/stockView")
public class StockViewController extends BaseController {
    @Autowired
    private RepoService repoService;
    @Autowired
    private StockManageService stockManageService;

    @RequestMapping(value = {"list", ""})
    public String list(Model model){
        List<Repo> repoList = repoService.getAllRepo();//仓库
        model.addAttribute("repoList",repoList);
        return "ims/stock/stockView/stockView";
    }

    /**
     * 库存列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "stockList")
    @ResponseBody
    public LayuiPageInfo orderList(@RequestBody Map<String,Object> params){
        LayuiPageInfo<Stock> pageInfo=null;
        try{
            pageInfo=stockManageService.getStockPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

}

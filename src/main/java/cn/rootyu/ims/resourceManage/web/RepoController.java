package cn.rootyu.ims.resourceManage.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.resourceManage.entity.Repo;
import cn.rootyu.ims.resourceManage.service.RepoService;
import cn.rootyu.rad.common.utils.ConstantUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RepoController
 * @Description 仓库Controller
 * @Author yuhui
 * @Date 2019/4/13 14:15
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/resourceManage/repo")
public class RepoController extends BaseController {

    @Autowired
    private RepoService repoService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = {"list", ""})
    public String list() {
        return "ims/resourceManage/repo/repoList";
    }

    /**
     * 列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "repoList")
    @ResponseBody
    public LayuiPageInfo repoList(@RequestBody Map<String,Object> params){
        LayuiPageInfo<Repo> pageInfo=null;
        try{
            pageInfo=repoService.getRepoPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    @RequestMapping(value = "initAddRepo")
    public String initAddRepo(Model model) {
        List<User> adminList = systemService.findUserByOfficeId(ConstantUtils.STORE_DEPARTMENT);//保障部门所有员工
        model.addAttribute("userList",adminList);
        return "ims/resourceManage/repo/repoAdd";
    }

    @RequestMapping(value = "initEditRepo")
    public String initEditRepo(String id, String readonly, HttpServletRequest request, Model model) {
        Repo repo = repoService.getRepo(id);
        request.setAttribute("repo",repo);
        List<User> adminList = systemService.findUserByOfficeId(ConstantUtils.STORE_DEPARTMENT);//保障部门所有员工
        model.addAttribute("userList",adminList);
        if ("true".equals(readonly)){
            request.setAttribute("readonly",true);
        }
        return "ims/resourceManage/repo/repoEdit";
    }

    /**
     * 新增仓库信息
     * @param repo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addRepo")
    @ResponseBody
    public ResultBean addRepo(Repo repo){
        ResultBean resultBean=new ResultBean();
        try{
            repoService.addRepo(repo);
            resultBean.setData(repo.getId());
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
     * 修改仓库信息
     * @param repo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateRepo")
    @ResponseBody
    public ResultBean updateRepo(Repo repo){
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(repo.getId(),"更新主键不能为空");
            repoService.updateRepo(repo);
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
    @RequestMapping(value = "delRepo")
    @ResponseBody
    public ResultBean delRepo(String id){
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            repoService.removeRepo(idArray);//逻辑删除
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

package cn.rootyu.ims.resourceManage.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.resourceManage.dao.RepoDao;
import cn.rootyu.ims.resourceManage.entity.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RepoService
 * @Description 仓库Service
 * @Author yuhui
 * @Date 2019/4/13 14:15
 * @Version 1.0
 */
@Service
public class RepoService {
    @Autowired
    private RepoDao repoDao;

    public LayuiPageInfo<Repo> getRepoPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Repo> list = repoDao.selectRepoList(params);
        return new LayuiPageInfo<>(list);
    }

    public Repo getRepo(String id){
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Repo> list = repoDao.selectRepoList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Repo();
    }


    public void addRepo(Repo repo) {
        repo.preInsert();
        repoDao.addRepo(repo);
    }

    public void updateRepo(Repo repo) {
        repo.preUpdate();
        repoDao.updateRepo(repo);
    }

    public void removeRepo(String[] idArray) {
        repoDao.removeRepo(idArray);
    }

    public List<Repo> getAllRepo(){
        return repoDao.getAllRepo();
    }
}

package cn.rootyu.ims.resourceManage.dao;

import cn.rootyu.ims.resourceManage.entity.Repo;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface RepoDao {

    List<Repo> selectRepoList(Map<String,Object> params);

    void addRepo(Repo repo);

    void updateRepo(Repo repo);

    void removeRepo(String[] idArray);

    List<Repo> getAllRepo();
}

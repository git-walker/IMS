package cn.rootyu.ims.common.dao;

//import cn.rootyu.ncr.ncr.entity.Ncr;
import cn.rootyu.ims.common.entity.CodeValue;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.User;
//import cn.rootyu.queuing.entity.JobStatistic;
//import cn.rootyu.queuing.entity.Queuing;

import java.util.List;

@MyBatisDao
public interface CodeValueDao extends CrudDao<CodeValue> {

	//查询故障列表（不删）
//	List<CodeValue> findFaultList(CodeValue param);
	//检验计划列表
	List<CodeValue> findPlanList(CodeValue param);
	//项目分类
	List<CodeValue> findSystemList(CodeValue param);
	//查询所有项目列表
	List<CodeValue> findProjectList(CodeValue param);
	//查询未结项的项目列表
	List<CodeValue> findProjectGoingList(CodeValue param);
	//项目列表 根据名称排序
	List<CodeValue> findProjectListOrder(CodeValue param);
	
	List<CodeValue> findStationList(CodeValue param);
	
	List<CodeValue> findStationList1(CodeValue param);
	
	List<CodeValue> findWPList(CodeValue param);
	
	List<CodeValue> findTargetList(CodeValue param);
	
	List<CodeValue> findToolList(CodeValue param);
	
	List<CodeValue> findUserList(CodeValue param);
	
	List<CodeValue> findRoleList(CodeValue param);
	
	List<CodeValue> findPointByTask(CodeValue param);
	
	List<CodeValue> findProviderList(CodeValue param);
	
	List<CodeValue> findGroupByPlan(CodeValue param);
	
	List<CodeValue> findStationByList(CodeValue param);
	
	List<CodeValue> findPointByList(CodeValue param);
	
	List<CodeValue> findQualityList(CodeValue param);
	
	List<CodeValue> findControlList(CodeValue param);
	
	User findUserByPointId(String pointId);
	
	List<User> userByRole(String id);
	
	List<CodeValue> findUserByRole(String id);

	List<CodeValue> findUserByRoleEnName(String enName);

	List<String> roleByUser(String id);
	
	String findUserName(String id);

	List<CodeValue> findTaskStatusList(CodeValue param);
	//根据id查询User
	User findUserById(String id);
	//根据角色查询User
	List<CodeValue> findUserListByRole(String role);
	//根据工位查询User
	List<User> findUserListByStation(String station);
	//查询组织结构
	List<CodeValue> findOfficeList(CodeValue param);
	//根据组织机构查人
	List<CodeValue> findUserByOffice(String id);
	//根据人查组织机构
	CodeValue findOfficeByUser(String id);
	//根据组织id查在组织机构名称
	CodeValue findOfficeById(String id);
	//根据项目id查项目名字
	CodeValue findProjectName(String projectId);
	//查字典表列表
	List<CodeValue> getDictList(String type);
	//根据部门查询User
	List<CodeValue> findUserListByOffice(CodeValue param);

    List<CodeValue> findQueuingTypeList();

	List<CodeValue> findUnitList(CodeValue codeValue);

	List<CodeValue> findUser();

//    List<CodeValue> getProjectView(Ncr ncr);

	List<CodeValue> jobTypeList();

//    List<CodeValue> getQueuingCodeListByJob(JobStatistic jobStatistic);
//
//    List<CodeValue> findMeetCodeList(JobStatistic jobStatistic);
//
//    List<CodeValue> findZgCodeList(Queuing queuing);

	List<CodeValue> findTypeList();

    List<CodeValue> getResponUnitNamePage(Page page);
    List<CodeValue> getInnerUnitNamePage(Page page);

    List<CodeValue> getCheckPersonPage(Page<CodeValue> page);
    
}

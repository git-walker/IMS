package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.dao.CrudDao;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Menu;
import cn.rootyu.rad.modules.sys.entity.User;

import java.util.List;

/**
 * 用户DAO接口
 * @author yuhui
 * @version 1.0
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param user
	 * @return
	 */
	User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	int updateUserInfo(User user);

	
	/**
	 * 根据用户Id查询用户所有可显示的任务
	 * @param id
	 * @return
	 */
	List<Menu> findMenuListByUser(String id);


	/**
	 * 批量修改用户密码
	 * @param entryptPassword
	 */
	int updateNoPwdUser(String entryptPassword);
	
	List<User> findUserList(String roleId);

	/**
	 * 设置用户禁止登录
	 * @param user
	 */
	void updateLoginDisabled(User user);
}

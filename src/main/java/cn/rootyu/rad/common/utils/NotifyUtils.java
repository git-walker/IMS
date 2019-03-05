package cn.rootyu.rad.common.utils;

import cn.rootyu.rad.modules.sys.dao.NotifyDao;
import cn.rootyu.rad.modules.sys.entity.Notify;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

import java.util.List;

public class NotifyUtils {
	
	private static NotifyDao notifyDao = SpringContextHolder.getBean(NotifyDao.class);

	// -- Notify Service --//
	/**
	 * 发送通知
	 * 
	 * @param title
	 *            通知标题
	 * @param content
	 *            通知内容
	 * @param isUrgent
	 *            是否紧急：1 紧急，0或null 非紧急
	 * @param senders
	 *            接受用户列表
	 * @param isSysterm
	 *            是否系统通知
	 * @param type
	 *            通知类型
	 */
	public static boolean notify(String title, String content, boolean isUrgent,
                                 List<User> senders, boolean isSysterm, String type) {
		List<Notify> notifyList = Lists.newArrayList();
		for (User user : senders) {
			Notify notify = new Notify();
			notify.preInsert();
			notify.setTitle(title);
			notify.setContent(content);
			notify.setUrgentFlag(isUrgent ? "1" : "0");
			if (user.getLoginName() == null || user.getLoginName().equals("")) {
				user = UserUtils.get(user.getId());
			}
			notify.setReceiver(user);
			notify.setReadFlag("0");
			if (!isSysterm) {
				notify.setSenderId(UserUtils.getUser().getId());
			} else {
				notify.setSenderId("");
			}
			notify.setType(type);
			notifyList.add(notify);
		}
		int result = notifyDao.insertAll(notifyList);
		return result != 0;
	}

}

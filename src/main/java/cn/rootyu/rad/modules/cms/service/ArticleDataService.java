/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.cms.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.cms.dao.ArticleDataDao;
import cn.rootyu.rad.modules.cms.entity.ArticleData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站点Service
 * @author DHC
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}

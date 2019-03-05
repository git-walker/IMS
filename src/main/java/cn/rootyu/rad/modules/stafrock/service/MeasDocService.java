package cn.rootyu.rad.modules.stafrock.service;


import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.MeasDocDao;
import cn.rootyu.rad.modules.supply.entity.MeasDoc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 计量档案Service
 * @author xuxiujun
 * @version 2015-10-27
 */
@Service
@Transactional
public class MeasDocService extends CrudService<MeasDocDao, MeasDoc> {

	public List<MeasDoc> findmeasList() {
		return dao.findmeasList();
	}
}

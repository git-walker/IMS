package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.InvclDao;
import cn.rootyu.rad.modules.stafrock.entity.Invcl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 存货分类Service
 * @author xiujun.xu
 * @version 2015-11-28
 */
@Service
@Transactional
public class InvclService extends CrudService<InvclDao, Invcl> {
	
	public List<Invcl> findList(Invcl invcl) {
		return dao.findList(invcl);
	}
}


package cn.rootyu.ims.basisData.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.ims.basisData.dao.QmisProvideDao;
import cn.rootyu.ims.basisData.entity.QmisProvide;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QmisProvideService extends CrudService<QmisProvideDao, QmisProvide> {
    /**
     * 查询类型列表
     * @return
     */
    public List<String> findTypeList(){
        return dao.findTypeList();
    }

    public void insert(QmisProvide provide) {
         dao.insert(provide);
    }

    public void updata(QmisProvide provide) {
        dao.update(provide);
    }
    public void batchDelete(String[] ids) {
        for(int i=0;i<ids.length;i++){
            QmisProvide provide = new QmisProvide();
            provide.setId(ids[i]);
            super.delete(provide);
        }
    }

}

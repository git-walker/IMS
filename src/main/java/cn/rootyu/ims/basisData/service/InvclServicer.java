package cn.rootyu.ims.basisData.service;

import java.util.List;

import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rootyu.ims.basisData.dao.InvcllDao;
import cn.rootyu.ims.basisData.entity.Project;

@Service
@Transactional(readOnly = true)
public class InvclServicer extends CrudService<InvcllDao, Project> {
	
		public List<String> findTypeList() {
        	 return dao.findTypeList(new Project());			
		}

        @Transactional(readOnly = false)
		@Override
        public void save(Project Project) {
       	 super.save(Project);		
    	}

         @Transactional(readOnly = false)
		 @Override
     	public void delete(Project Project) {
     		super.delete(Project);
     	}

         @Transactional(readOnly = false)
     	public void batchDelete(String[] ids) {
         	for (int i = 0; i < ids.length; i++) {
         		Project Project = new Project();
         		Project.setId(ids[i]);
         		super.delete(Project);
         	}
     	}
}

package cn.rootyu.ims.webService.service;

import cn.rootyu.ims.webService.entity.NocmTaskInfoWS;
import cn.rootyu.ims.webService.entity.NocmTaskResultWS;
import cn.rootyu.ims.webService.entity.NocmTaskWceWS;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;


@WebService
public interface NocmService {

	//测试方法
	@WebMethod
	public String inspect(NocmTaskWceWS nocmTaskWce, List<NocmTaskResultWS> resultList) throws Exception;
//	public String inspect(@WebParam (name="text")String text);
	
	// 查询任务列表
	@WebMethod
	public List<NocmTaskInfoWS> findTaskList(NocmTaskInfoWS entity);
	
	// 根据任务查询工件列表
	@WebMethod
	public List<NocmTaskWceWS> findTaskWceList(NocmTaskWceWS query);
	
	// 根据工件查询项点列表
	@WebMethod
	public List<NocmTaskResultWS> findPointList(NocmTaskResultWS query);
	
	@WebMethod
	public List<NocmTaskInfoWS> findAll();
}

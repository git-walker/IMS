package cn.rootyu.ims.webService.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface MessageService {

	//测试方法
	public String getMessage(@WebParam(name = "text") String text);
}

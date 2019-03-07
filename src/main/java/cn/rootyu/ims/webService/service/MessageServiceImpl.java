package cn.rootyu.ims.webService.service;

import javax.jws.WebService;


@WebService(endpointInterface= "cn.rootyu.ims.webService.service.MessageService", serviceName ="MessageService")
public class MessageServiceImpl  implements  MessageService{

	public String getMessage(String text) {
		// TODO Auto-generated method stub
		return "Welcome" +text;
	}

}

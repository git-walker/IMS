package cn.rootyu.ims.webService.service;

import javax.jws.WebService;

//生成客户端代码
//wsdl2java -client -p com.dhc.ims.webService.client -d e:/ http://localhost:8080/QMIS/webService/MessageService?wsdl


@WebService(endpointInterface= "cn.rootyu.ims.webService.service.MessageService", serviceName ="MessageService")
public class MessageServiceImpl  implements  MessageService{

	public String getMessage(String text) {
		// TODO Auto-generated method stub
		return "Welcome" +text;
	}

}

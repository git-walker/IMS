package cn.rootyu.ims.webService.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.2
 * 2017-02-15T10:24:38.500+08:00
 * Generated source version: 3.0.2
 * 
 */
@WebService(targetNamespace = "http://service.webService.ims.dhc.com/", name = "MessageService")
@XmlSeeAlso({ObjectFactory.class})
public interface MessageService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMessage", targetNamespace = "http://service.webService.ims.dhc.com/", className = "GetMessage")
    @WebMethod
    @ResponseWrapper(localName = "getMessageResponse", targetNamespace = "http://service.webService.ims.dhc.com/", className = "GetMessageResponse")
    public String getMessage(
            @WebParam(name = "text", targetNamespace = "")
                    String text
    );
}

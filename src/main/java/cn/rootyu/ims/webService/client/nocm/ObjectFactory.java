
package cn.rootyu.ims.webService.client.nocm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dhc.ims.webservice.service package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InspectResponse_QNAME = new QName("http://service.webService.ims.dhc.com/", "inspectResponse");
    private final static QName _Inspect_QNAME = new QName("http://service.webService.ims.dhc.com/", "inspect");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dhc.ims.webservice.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InspectResponse }
     * 
     */
    public InspectResponse createInspectResponse() {
        return new InspectResponse();
    }

    /**
     * Create an instance of {@link Inspect }
     * 
     */
    public Inspect createInspect() {
        return new Inspect();
    }

    /**
     * Create an instance of {@link NocmTaskResult }
     * 
     */
    public NocmTaskResult createNocmTaskResult() {
        return new NocmTaskResult();
    }

    /**
     * Create an instance of {@link NocmTaskWce }
     * 
     */
    public NocmTaskWce createNocmTaskWce() {
        return new NocmTaskWce();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InspectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webService.ims.dhc.com/", name = "inspectResponse")
    public JAXBElement<InspectResponse> createInspectResponse(InspectResponse value) {
        return new JAXBElement<InspectResponse>(_InspectResponse_QNAME, InspectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Inspect }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webService.ims.dhc.com/", name = "inspect")
    public JAXBElement<Inspect> createInspect(Inspect value) {
        return new JAXBElement<Inspect>(_Inspect_QNAME, Inspect.class, null, value);
    }

}


package pe.com.bn.mgtt.infraestructura.service.external.domain;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "ParametroInterfazKeyService", targetNamespace = "http://service.ws.comp.bn.com.pe", wsdlLocation = "WEB-INF/wsdl/ParametroInterfazKey.wsdl")
public class ParametroInterfazKeyService
    extends Service
{

    private final static URL PARAMETROINTERFAZKEYSERVICE_WSDL_LOCATION;
    private final static WebServiceException PARAMETROINTERFAZKEYSERVICE_EXCEPTION;
    private final static QName PARAMETROINTERFAZKEYSERVICE_QNAME = new QName("http://service.ws.comp.bn.com.pe", "ParametroInterfazKeyService");

    static {
            PARAMETROINTERFAZKEYSERVICE_WSDL_LOCATION = pe.com.bn.mgtt.infraestructura.service.external.domain.ParametroInterfazKeyService.class.getResource("/WEB-INF/wsdl/ParametroInterfazKey.wsdl");
        WebServiceException e = null;
        if (PARAMETROINTERFAZKEYSERVICE_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find 'WEB-INF/wsdl/ParametroInterfazKey.wsdl' wsdl. Place the resource correctly in the classpath.");
        }
        PARAMETROINTERFAZKEYSERVICE_EXCEPTION = e;
    }

    public ParametroInterfazKeyService() {
        super(__getWsdlLocation(), PARAMETROINTERFAZKEYSERVICE_QNAME);
    }

    public ParametroInterfazKeyService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PARAMETROINTERFAZKEYSERVICE_QNAME, features);
    }

    public ParametroInterfazKeyService(URL wsdlLocation) {
        super(wsdlLocation, PARAMETROINTERFAZKEYSERVICE_QNAME);
    }

    public ParametroInterfazKeyService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PARAMETROINTERFAZKEYSERVICE_QNAME, features);
    }

    public ParametroInterfazKeyService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ParametroInterfazKeyService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ParametroInterfazKey
     */
    @WebEndpoint(name = "ParametroInterfazKey")
    public ParametroInterfazKey getParametroInterfazKey() {
        return super.getPort(new QName("http://service.ws.comp.bn.com.pe", "ParametroInterfazKey"), ParametroInterfazKey.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ParametroInterfazKey
     */
    @WebEndpoint(name = "ParametroInterfazKey")
    public ParametroInterfazKey getParametroInterfazKey(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.ws.comp.bn.com.pe", "ParametroInterfazKey"), ParametroInterfazKey.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PARAMETROINTERFAZKEYSERVICE_EXCEPTION!= null) {
            throw PARAMETROINTERFAZKEYSERVICE_EXCEPTION;
        }
        return PARAMETROINTERFAZKEYSERVICE_WSDL_LOCATION;
    }

}

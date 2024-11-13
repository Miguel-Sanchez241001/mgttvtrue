
package pe.com.bn.mgtt.infraestructura.service.external.domain;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "ParametroInterfazKey", targetNamespace = "http://service.ws.comp.bn.com.pe")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ParametroInterfazKey {


    /**
     * 
     * @param semilla
     * @param llave
     * @param sistema
     * @param idUsuario
     * @param cuenta
     * @return
     *     returns pe.com.bn.mgtt.infraestructura.service.external.domain.SistemaParametro
     */
    @WebMethod(action = "datoParametroService")
    @WebResult(name = "datoParametroServiceReturn", targetNamespace = "")
    @RequestWrapper(localName = "datoParametroService", targetNamespace = "http://service.ws.comp.bn.com.pe", className = "pe.com.bn.mgtt.infraestructura.service.external.domain.DatoParametroService")
    @ResponseWrapper(localName = "datoParametroServiceResponse", targetNamespace = "http://service.ws.comp.bn.com.pe", className = "pe.com.bn.mgtt.infraestructura.service.external.domain.DatoParametroServiceResponse")
    public SistemaParametro datoParametroService(
        @WebParam(name = "sistema", targetNamespace = "")
        String sistema,
        @WebParam(name = "cuenta", targetNamespace = "")
        String cuenta,
        @WebParam(name = "semilla", targetNamespace = "")
        String semilla,
        @WebParam(name = "llave", targetNamespace = "")
        byte[] llave,
        @WebParam(name = "idUsuario", targetNamespace = "")
        String idUsuario);

}


package pe.com.bn.mgtt.infraestructura.service.external.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datoParametroServiceReturn" type="{http://bean.ws.comp.bn.com.pe}SistemaParametro"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datoParametroServiceReturn"
})
@XmlRootElement(name = "datoParametroServiceResponse", namespace = "http://service.ws.comp.bn.com.pe")
public class DatoParametroServiceResponse
    implements Serializable
{

    @XmlElement(required = true, nillable = true)
    protected SistemaParametro datoParametroServiceReturn;

    /**
     * Obtiene el valor de la propiedad datoParametroServiceReturn.
     * 
     * @return
     *     possible object is
     *     {@link SistemaParametro }
     *     
     */
    public SistemaParametro getDatoParametroServiceReturn() {
        return datoParametroServiceReturn;
    }

    /**
     * Define el valor de la propiedad datoParametroServiceReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link SistemaParametro }
     *     
     */
    public void setDatoParametroServiceReturn(SistemaParametro value) {
        this.datoParametroServiceReturn = value;
    }

}

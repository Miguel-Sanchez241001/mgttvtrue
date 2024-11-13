
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
 *         &lt;element name="sistema" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cuenta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="semilla" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="llave" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="idUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "sistema",
    "cuenta",
    "semilla",
    "llave",
    "idUsuario"
})
@XmlRootElement(name = "datoParametroService", namespace = "http://service.ws.comp.bn.com.pe")
public class DatoParametroService
    implements Serializable
{

    @XmlElement(required = true, nillable = true)
    protected String sistema;
    @XmlElement(required = true, nillable = true)
    protected String cuenta;
    @XmlElement(required = true, nillable = true)
    protected String semilla;
    @XmlElement(required = true)
    protected byte[] llave;
    @XmlElement(required = true, nillable = true)
    protected String idUsuario;

    /**
     * Obtiene el valor de la propiedad sistema.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * Define el valor de la propiedad sistema.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSistema(String value) {
        this.sistema = value;
    }

    /**
     * Obtiene el valor de la propiedad cuenta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * Define el valor de la propiedad cuenta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuenta(String value) {
        this.cuenta = value;
    }

    /**
     * Obtiene el valor de la propiedad semilla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSemilla() {
        return semilla;
    }

    /**
     * Define el valor de la propiedad semilla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSemilla(String value) {
        this.semilla = value;
    }

    /**
     * Obtiene el valor de la propiedad llave.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getLlave() {
        return llave;
    }

    /**
     * Define el valor de la propiedad llave.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setLlave(byte[] value) {
        this.llave = value;
    }

    /**
     * Obtiene el valor de la propiedad idUsuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Define el valor de la propiedad idUsuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUsuario(String value) {
        this.idUsuario = value;
    }

}

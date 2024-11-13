
package pe.com.bn.mgtt.infraestructura.service.external.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Parametro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Parametro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aliasParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="campoParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descripcionParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Parametro", propOrder = {
    "aliasParam",
    "campoParam",
    "descripcionParam",
    "tipoParam",
    "valorParam"
})
public class Parametro
    implements Serializable
{

    @XmlElement(required = true, nillable = true)
    protected String aliasParam;
    @XmlElement(required = true, nillable = true)
    protected String campoParam;
    @XmlElement(required = true, nillable = true)
    protected String descripcionParam;
    @XmlElement(required = true, nillable = true)
    protected String tipoParam;
    @XmlElement(required = true, nillable = true)
    protected String valorParam;

    /**
     * Obtiene el valor de la propiedad aliasParam.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasParam() {
        return aliasParam;
    }

    /**
     * Define el valor de la propiedad aliasParam.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasParam(String value) {
        this.aliasParam = value;
    }

    /**
     * Obtiene el valor de la propiedad campoParam.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampoParam() {
        return campoParam;
    }

    /**
     * Define el valor de la propiedad campoParam.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampoParam(String value) {
        this.campoParam = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionParam.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionParam() {
        return descripcionParam;
    }

    /**
     * Define el valor de la propiedad descripcionParam.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionParam(String value) {
        this.descripcionParam = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoParam.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoParam() {
        return tipoParam;
    }

    /**
     * Define el valor de la propiedad tipoParam.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoParam(String value) {
        this.tipoParam = value;
    }

    /**
     * Obtiene el valor de la propiedad valorParam.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorParam() {
        return valorParam;
    }

    /**
     * Define el valor de la propiedad valorParam.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorParam(String value) {
        this.valorParam = value;
    }

}


package pe.com.bn.mgtt.infraestructura.service.external.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para GrupoParametro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="GrupoParametro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoGrupo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aliasDescripGrupo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aliasGrupo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parametro" type="{http://bean.ws.comp.bn.com.pe}ArrayOfParametro"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GrupoParametro", propOrder = {
    "tipoGrupo",
    "aliasDescripGrupo",
    "aliasGrupo",
    "parametro"
})
public class GrupoParametro
    implements Serializable
{

    @XmlElement(required = true, nillable = true)
    protected String tipoGrupo;
    @XmlElement(required = true, nillable = true)
    protected String aliasDescripGrupo;
    @XmlElement(required = true, nillable = true)
    protected String aliasGrupo;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfParametro parametro;

    /**
     * Obtiene el valor de la propiedad tipoGrupo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoGrupo() {
        return tipoGrupo;
    }

    /**
     * Define el valor de la propiedad tipoGrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoGrupo(String value) {
        this.tipoGrupo = value;
    }

    /**
     * Obtiene el valor de la propiedad aliasDescripGrupo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasDescripGrupo() {
        return aliasDescripGrupo;
    }

    /**
     * Define el valor de la propiedad aliasDescripGrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasDescripGrupo(String value) {
        this.aliasDescripGrupo = value;
    }

    /**
     * Obtiene el valor de la propiedad aliasGrupo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasGrupo() {
        return aliasGrupo;
    }

    /**
     * Define el valor de la propiedad aliasGrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasGrupo(String value) {
        this.aliasGrupo = value;
    }

    /**
     * Obtiene el valor de la propiedad parametro.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParametro }
     *     
     */
    public ArrayOfParametro getParametro() {
        return parametro;
    }

    /**
     * Define el valor de la propiedad parametro.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParametro }
     *     
     */
    public void setParametro(ArrayOfParametro value) {
        this.parametro = value;
    }

}

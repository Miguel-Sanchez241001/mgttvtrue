
package pe.com.bn.mgtt.infraestructura.service.external.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SistemaParametro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SistemaParametro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aliasSistema" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="grupoParametro" type="{http://bean.ws.comp.bn.com.pe}ArrayOfGrupoParametro"/>
 *         &lt;element name="proceso" type="{http://bean.ws.comp.bn.com.pe}ReturnProceso"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SistemaParametro", propOrder = {
    "aliasSistema",
    "grupoParametro",
    "proceso"
})
public class SistemaParametro
    implements Serializable
{

    @XmlElement(required = true, nillable = true)
    protected String aliasSistema;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfGrupoParametro grupoParametro;
    @XmlElement(required = true, nillable = true)
    protected ReturnProceso proceso;

    /**
     * Obtiene el valor de la propiedad aliasSistema.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasSistema() {
        return aliasSistema;
    }

    /**
     * Define el valor de la propiedad aliasSistema.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasSistema(String value) {
        this.aliasSistema = value;
    }

    /**
     * Obtiene el valor de la propiedad grupoParametro.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGrupoParametro }
     *     
     */
    public ArrayOfGrupoParametro getGrupoParametro() {
        return grupoParametro;
    }

    /**
     * Define el valor de la propiedad grupoParametro.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGrupoParametro }
     *     
     */
    public void setGrupoParametro(ArrayOfGrupoParametro value) {
        this.grupoParametro = value;
    }

    /**
     * Obtiene el valor de la propiedad proceso.
     * 
     * @return
     *     possible object is
     *     {@link ReturnProceso }
     *     
     */
    public ReturnProceso getProceso() {
        return proceso;
    }

    /**
     * Define el valor de la propiedad proceso.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnProceso }
     *     
     */
    public void setProceso(ReturnProceso value) {
        this.proceso = value;
    }

}

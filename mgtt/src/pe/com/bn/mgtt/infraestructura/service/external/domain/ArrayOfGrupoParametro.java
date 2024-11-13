
package pe.com.bn.mgtt.infraestructura.service.external.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfGrupoParametro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGrupoParametro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GrupoParametro" type="{http://bean.ws.comp.bn.com.pe}GrupoParametro" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGrupoParametro", propOrder = {
    "grupoParametro"
})
public class ArrayOfGrupoParametro
    implements Serializable
{

    @XmlElement(name = "GrupoParametro", nillable = true)
    protected List<GrupoParametro> grupoParametro;

    /**
     * Gets the value of the grupoParametro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grupoParametro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrupoParametro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrupoParametro }
     * 
     * 
     */
    public List<GrupoParametro> getGrupoParametro() {
        if (grupoParametro == null) {
            grupoParametro = new ArrayList<GrupoParametro>();
        }
        return this.grupoParametro;
    }

}

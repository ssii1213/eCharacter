//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.6 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.06.27 a las 01:32:19 PM CEST 
//


package es.eucm.echaracter.data.model;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="mainMesh" type="{}mainMeshType"/>
 *         &lt;element name="subMesh" type="{}subMeshType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="languagesPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "mainMesh",
    "subMesh",
    "languagesPath"
})
@XmlRootElement(name = "model")
public class Model {

    @XmlElement(required = true)
    protected MainMeshType mainMesh;
    protected List<SubMeshType> subMesh;
    @XmlElement(required = true)
    protected String languagesPath;

    /**
     * Obtiene el valor de la propiedad mainMesh.
     * 
     * @return
     *     possible object is
     *     {@link MainMeshType }
     *     
     */
    public MainMeshType getMainMesh() {
        return mainMesh;
    }

    /**
     * Define el valor de la propiedad mainMesh.
     * 
     * @param value
     *     allowed object is
     *     {@link MainMeshType }
     *     
     */
    public void setMainMesh(MainMeshType value) {
        this.mainMesh = value;
    }

    /**
     * Gets the value of the subMesh property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subMesh property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubMesh().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubMeshType }
     * 
     * 
     */
    public List<SubMeshType> getSubMesh() {
        if (subMesh == null) {
            subMesh = new ArrayList<SubMeshType>();
        }
        return this.subMesh;
    }

    /**
     * Obtiene el valor de la propiedad languagesPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguagesPath() {
        return languagesPath;
    }

    /**
     * Define el valor de la propiedad languagesPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguagesPath(String value) {
        this.languagesPath = value;
    }

}

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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para multiOptionTextureType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="multiOptionTextureType">
 *   &lt;complexContent>
 *     &lt;extension base="{}textureType">
 *       &lt;sequence>
 *         &lt;element name="optionTexture" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="iconPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="idSubTexture" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                 &lt;attribute name="default" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="text" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multiOptionTextureType", propOrder = {
    "optionTexture"
})
public class MultiOptionTextureType
    extends TextureType
{

    @XmlElement(required = true)
    protected List<MultiOptionTextureType.OptionTexture> optionTexture;

    /**
     * Gets the value of the optionTexture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optionTexture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptionTexture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiOptionTextureType.OptionTexture }
     * 
     * 
     */
    public List<MultiOptionTextureType.OptionTexture> getOptionTexture() {
        if (optionTexture == null) {
            optionTexture = new ArrayList<MultiOptionTextureType.OptionTexture>();
        }
        return this.optionTexture;
    }


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
     *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="iconPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *       &lt;attribute name="idSubTexture" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *       &lt;attribute name="default" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="text" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "path",
        "iconPath"
    })
    public static class OptionTexture {

        @XmlElement(required = true)
        protected String path;
        @XmlElement(required = true)
        protected String iconPath;
        @XmlAttribute(name = "idSubTexture", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String idSubTexture;
        @XmlAttribute(name = "default", required = true)
        protected boolean _default;
        @XmlAttribute(name = "text", required = true)
        protected String text;

        /**
         * Obtiene el valor de la propiedad path.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPath() {
            return path;
        }

        /**
         * Define el valor de la propiedad path.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPath(String value) {
            this.path = value;
        }

        /**
         * Obtiene el valor de la propiedad iconPath.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIconPath() {
            return iconPath;
        }

        /**
         * Define el valor de la propiedad iconPath.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIconPath(String value) {
            this.iconPath = value;
        }

        /**
         * Obtiene el valor de la propiedad idSubTexture.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdSubTexture() {
            return idSubTexture;
        }

        /**
         * Define el valor de la propiedad idSubTexture.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdSubTexture(String value) {
            this.idSubTexture = value;
        }

        /**
         * Obtiene el valor de la propiedad default.
         * 
         */
        public boolean isDefault() {
            return _default;
        }

        /**
         * Define el valor de la propiedad default.
         * 
         */
        public void setDefault(boolean value) {
            this._default = value;
        }

        /**
         * Obtiene el valor de la propiedad text.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getText() {
            return text;
        }

        /**
         * Define el valor de la propiedad text.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setText(String value) {
            this.text = value;
        }

    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.07 at 02:53:40 PM IDT 
//


package XmlParsing.JaxbClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Words" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="exclude-chars" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "words"
})
@XmlRootElement(name = "Dictionary")
public class Dictionary {

    @XmlElement(name = "Words", required = true)
    protected String words;
    @XmlAttribute(name = "exclude-chars", required = true)
    protected String excludeChars;

    /**
     * Gets the value of the words property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWords() {
        return words;
    }

    /**
     * Sets the value of the words property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWords(String value) {
        this.words = value;
    }

    /**
     * Gets the value of the excludeChars property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcludeChars() {
        return excludeChars;
    }

    /**
     * Sets the value of the excludeChars property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcludeChars(String value) {
        this.excludeChars = value;
    }

}

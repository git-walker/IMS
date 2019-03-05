
package cn.rootyu.ims.webService.client.nocm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for nocmTaskResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nocmTaskResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="downValue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="normValue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pointId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultValue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taskWceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upValue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="updateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="valueType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yl1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yl2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nocmTaskResult", propOrder = {
    "createBy",
    "createDate",
    "downValue",
    "flag",
    "id",
    "normValue",
    "pointId",
    "resultValue",
    "status",
    "taskWceId",
    "upValue",
    "updateBy",
    "updateDate",
    "valueType",
    "yl1",
    "yl2"
})
public class NocmTaskResult {

    protected String createBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    protected float downValue;
    protected String flag;
    protected String id;
    protected float normValue;
    protected String pointId;
    protected float resultValue;
    protected String status;
    protected String taskWceId;
    protected float upValue;
    protected String updateBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDate;
    protected String valueType;
    protected String yl1;
    protected String yl2;

    /**
     * Gets the value of the createBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Sets the value of the createBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateBy(String value) {
        this.createBy = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the downValue property.
     * 
     */
    public float getDownValue() {
        return downValue;
    }

    /**
     * Sets the value of the downValue property.
     * 
     */
    public void setDownValue(float value) {
        this.downValue = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the normValue property.
     * 
     */
    public float getNormValue() {
        return normValue;
    }

    /**
     * Sets the value of the normValue property.
     * 
     */
    public void setNormValue(float value) {
        this.normValue = value;
    }

    /**
     * Gets the value of the pointId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointId() {
        return pointId;
    }

    /**
     * Sets the value of the pointId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointId(String value) {
        this.pointId = value;
    }

    /**
     * Gets the value of the resultValue property.
     * 
     */
    public float getResultValue() {
        return resultValue;
    }

    /**
     * Sets the value of the resultValue property.
     * 
     */
    public void setResultValue(float value) {
        this.resultValue = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the taskWceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskWceId() {
        return taskWceId;
    }

    /**
     * Sets the value of the taskWceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskWceId(String value) {
        this.taskWceId = value;
    }

    /**
     * Gets the value of the upValue property.
     * 
     */
    public float getUpValue() {
        return upValue;
    }

    /**
     * Sets the value of the upValue property.
     * 
     */
    public void setUpValue(float value) {
        this.upValue = value;
    }

    /**
     * Gets the value of the updateBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Sets the value of the updateBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateBy(String value) {
        this.updateBy = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDate(XMLGregorianCalendar value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the valueType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * Sets the value of the valueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueType(String value) {
        this.valueType = value;
    }

    /**
     * Gets the value of the yl1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYl1() {
        return yl1;
    }

    /**
     * Sets the value of the yl1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYl1(String value) {
        this.yl1 = value;
    }

    /**
     * Gets the value of the yl2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYl2() {
        return yl2;
    }

    /**
     * Sets the value of the yl2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYl2(String value) {
        this.yl2 = value;
    }

}

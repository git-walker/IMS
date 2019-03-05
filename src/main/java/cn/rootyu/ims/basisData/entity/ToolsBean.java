package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 工具实体类
 * @author 李闯
 */
public class ToolsBean extends DataEntity<ToolsBean> {
	
	private static final long serialVersionUID = 1L;
	
	private String toolsName;//工具名称
	
	private String toolsModel;//工具尺寸
	
	private String toolsType;//工具类型
	
	private String toolsUnit;//工具单位
	
	private String type;

	
	public ToolsBean(String toolsId, String toolsName, String toolsModel, String toolsType,
		 String toolsUnit) {
		super();
		this.toolsName = toolsName;
		this.toolsModel = toolsModel;
		this.toolsType = toolsType;
		this.toolsUnit = toolsUnit;
	}
	
	public ToolsBean() {
		super();
	}
	public ToolsBean(String toolsId){
		super(toolsId);
	}
	

	
	@Override
	public String toString() {
		return toolsName;
	}
	
	

	@XmlAttribute
	@Length(min=1, max=100)
	public String getToolsUnit() {
		return toolsUnit;
	}
	public void setToolsUnit(String toolsUnit) {
		this.toolsUnit = toolsUnit;
	}
	
	@XmlAttribute
	@Length(min=1, max=100)
	public String getToolsName() {
		return toolsName;
	}
	public void setToolsName(String toolsName) {
		this.toolsName = toolsName;
	}
	@XmlAttribute
	@Length(min=1, max=100)
	public String getToolsModel() {
		return toolsModel;
	}
	public void setToolsModel(String toolsModel) {
		this.toolsModel = toolsModel;
	}
	@XmlAttribute
	@Length(min=1, max=100)
	public String getToolsType() {
		return toolsType;
	}

	public void setToolsType(String toolsType) {
		this.toolsType = toolsType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	
}

package cn.rootyu.rad.modules.sys.entity;

import cn.rootyu.rad.common.entity.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域Entity
 * @author yuhui
 * @version 1.0
 */
public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;
	private String code; 	// 区域编码
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	private List<Area> subArea; //子区域集合
	
	public Area(){
		super();
		this.sort = 30;
	}

	public Area(String id){
		super(id);
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonIgnore
	public static String getRootId(){
		return "0";
	}
	
	@Override
	public String toString() {
		return name;
	}

	public List<Area> getSubArea() {
		if(subArea ==null){
			subArea = new ArrayList<Area>();
		}
		return subArea;
	}

	public void setSubArea(List<Area> subArea) {
		this.subArea = subArea;
	}
}
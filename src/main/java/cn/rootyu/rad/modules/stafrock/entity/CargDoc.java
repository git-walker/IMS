/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.TreeEntity;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @类功能说明：存储树 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-12-7 下午10:30:31  
 * @版本：V1.0
 */
@Alias("JobStorCargDoc")
public class CargDoc extends TreeEntity<CargDoc> {

	private static final long serialVersionUID = 1L;

	// --------------------------Entity---------------------------------
	private CargDoc parent;
	private String code; // 存储编号
	private String name; // 存储类型（货架，堆放区）
	private String reservoirId; // 所属库区
	private String grade; // 层级
	private List<CargDoc> subCargDoc;
	private String named;

	public CargDoc() {
		super();
	}

	public CargDoc(String id) {
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReservoirId() {
		return reservoirId;
	}

	public void setReservoirId(String reservoirId) {
		this.reservoirId = reservoirId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<CargDoc> getSubCargDoc() {
		if(subCargDoc == null){
			subCargDoc = new ArrayList<CargDoc>();
		}
		return subCargDoc;
	}

	public void setSubCargDoc(List<CargDoc> subCargDoc) {
		this.subCargDoc = subCargDoc;
	}
	
	public String getNamed() {
		return named;
	}

	public void setNamed(String named) {
		this.named = named;
	}

	@Override
	public CargDoc getParent() {
		return parent;
	}

	@Override
	public void setParent(CargDoc parent) {
		this.parent = parent;
	}


}
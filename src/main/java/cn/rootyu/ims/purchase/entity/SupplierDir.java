package cn.rootyu.ims.purchase.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName SupplierDir
 * @Description 供方名录
 * @Author yuhui
 * @Date 2019/4/14 16:27
 * @Version 1.0
 */
public class SupplierDir extends DataEntity<SupplierDir> {

    private String theme;
    private int year;
    private String createByName;//创建人

    public SupplierDir(){}

    public SupplierDir(int year){
        this.year=year;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }
}

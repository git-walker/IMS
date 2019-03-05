package cn.rootyu.ims.tools.entity;

import cn.rootyu.rad.common.persistence.BaseEntity;

/**
 * @ClassName SelectPage
 * @Description 集成select2分页类
 * @Authour qinyi
 * @Date 2018/8/27 13:51
 * @Version 1.0
 */
public class SelectPage extends BaseEntity<SelectPage> {

    private String text;

    private String seachMessage;

    private String param;

    @Override
    public void preInsert() {

    }

    @Override
    public void preUpdate() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSeachMessage() {
        return seachMessage;
    }

    public void setSeachMessage(String seachMessage) {
        this.seachMessage = seachMessage;
    }
}

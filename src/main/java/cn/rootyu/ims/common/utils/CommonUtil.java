package cn.rootyu.ims.common.utils;

import cn.rootyu.rad.modules.sys.utils.UserUtils;
import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class CommonUtil {

    public static void startPage(HttpServletRequest request){

        int pageNum=Integer.valueOf(request.getParameter("page"));
        int pageSize=Integer.valueOf(request.getParameter("limit"));
        if (pageSize>0){
            PageHelper.startPage(pageNum, pageSize);
        }
    }
    public static void startPage(Map<String, Object> params) {
        if (null != params) {
            Integer pageNum = (Integer) params.get("page");
            Integer pageSize = (Integer) params.get("limit");
            if (null != pageNum && null != pageSize && pageSize > 0)
                PageHelper.startPage(pageNum, pageSize);
        }
    }

    public static void setCommonParam(Map<String, Object> params){
        String userId= UserUtils.getUser().getId();
        Date now=new Date();
        params.put("createBy",userId);
        params.put("createDate",now);
        params.put("updateBy",userId);
        params.put("updateDate",now);
    }
}

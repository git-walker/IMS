package cn.rootyu.rad.modules.sys.listener;

import cn.rootyu.rad.modules.sys.service.SystemService;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @ClassName WebContextListener
 * @Description web项目启动监听
 * @Author yuhui
 * @Date 2019/3/21 14:34
 * @Version 1.0
 */
public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (!SystemService.printKeyLoadMessage()){


            return null;
        }
        return super.initWebApplicationContext(servletContext);
    }
}

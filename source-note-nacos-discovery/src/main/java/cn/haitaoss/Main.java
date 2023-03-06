package cn.haitaoss;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;

/**
 * @author haitao.chen
 * email haitaoss@aliyun.com
 * date 2023-03-06 15:07
 *
 */
@MyAnnotation
public class Main extends SpringBootServletInitializer {
    /**
     * 1. 启动嵌入式web容器后，会注册 WebServerStartStopLifecycle
     *      {@link AbstractApplicationContext#refresh()}
     *      {@link ServletWebServerApplicationContext#onRefresh()}
     *      {@link ServletWebServerApplicationContext#createWebServer()}
     *          若是嵌入式web容器的方式，那么就注册bean
     *          getBeanFactory().registerSingleton("webServerStartStop",new WebServerStartStopLifecycle(this, this.webServer));
     *
     * 2. 这个类型的bean，会在IOC容器refresh的最后阶段被回调
     *      {@link AbstractApplicationContext#finishRefresh()}
     *      {@link LifecycleProcessor#onRefresh()}
     *      {@link WebServerStartStopLifecycle#start()}
     *          使用事件广播器发布事件
     *          applicationContext.publishEvent(new ServletWebServerInitializedEvent(this.webServer, this.applicationContext));
     *
     * 3. spring-cloud-starter-alibaba-nacos-discovery.jar 的 META-INF/spring.factories 声明了自动配置类 NacosServiceRegistryAutoConfiguration
     *    这个类是一个 ApplicationListener ,是用来处理 ServletWebServerInitializedEvent 事件，处理的逻辑就是当前服务 注册到 nacos-server 中
     *    {@link NacosAutoServiceRegistration#onApplicationEvent(WebServerInitializedEvent)}
     *
     * Tips: 所以说，如果项目不是以嵌入式web容器方式启动的，那么 nacos 是无法注册到 nacos-server 的。
     *      但是注册逻辑也很简单，自己写代码也行
     * */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        System.out.println("no embed web container start...");
        return builder
                .sources(Main.class)
                .sources(NacosRegistry.class);  // 执行 nacos 注册服务的方法
    }

}

class NacosRegistry implements ApplicationListener<ContextRefreshedEvent>, ServletContextListener {
    @Autowired(required = false)
    private ServletContext servletContext;

    @Autowired(required = false)
    private NacosAutoServiceRegistration nacosAutoServiceRegistration;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /*
        可以这样子读到 tomcat 的启动端口号
        ((org.apache.catalina.connector.Connector[]) ((StandardService) ((StandardEngine) ((StandardService) ((ApplicationContext) ((ApplicationContextFacade) ((NacosRegistry) this).servletContext).context).service).engine).service).connectors)[0].getPort()*/
        nacosAutoServiceRegistration.setPort(8080);
        nacosAutoServiceRegistration.start();
    }
}

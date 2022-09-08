package nc.apps.configs;

import javax.servlet.*;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer  {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebMvcConfig.class);
        appContext.setServletContext(servletContext);

        appContext.scan("nc.apps");
        servletContext.addListener(new ContextLoaderListener(appContext));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "dispatcher", new DispatcherServlet(appContext));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("contextClass", appContext.getClass().getName());
      }
}
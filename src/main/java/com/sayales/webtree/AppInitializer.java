package com.sayales.webtree;

import com.sayales.webtree.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Pavel on 06.09.2018.
 */
public class AppInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DISPATCHER, new DispatcherServlet(context));
        servletRegistration.addMapping("/");
        servletRegistration.setLoadOnStartup(1);
    }
}

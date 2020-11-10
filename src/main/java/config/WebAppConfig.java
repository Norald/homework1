package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan({"controller","model","db"})
public class WebAppConfig extends WebMvcConfigurerAdapter {

    // Позволяет видеть все ресурсы в папке pages, такие как картинки, стили и т.п.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("WEB-INF/pages/**").addResourceLocations("WEB-INF/pages/**");
    }

    // а этот бин инициализирует View нашего проекта
    // точно это же мы делали в mvc-dispatcher-servlet.xml
    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("start");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/start").setViewName("start");
        registry.addViewController("/error").setViewName("error");
    }

}

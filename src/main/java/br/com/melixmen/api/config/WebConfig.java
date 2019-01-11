package br.com.melixmen.api.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"br.com.melixmen.api", "br.com.melixmen.api"})
@EntityScan("br.com.melixmen.api.models")
@EnableCaching()
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    @PostConstruct
    public void init() {
        LOG.debug("init() {} (debug={})", WebConfig.class.getSimpleName(), true);
    }

    @PreDestroy
    public void destroy() {
        LOG.debug("stop() {}", getClass().getSimpleName());
    }
}
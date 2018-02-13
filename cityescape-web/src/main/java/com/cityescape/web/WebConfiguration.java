package com.cityescape.web;

import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration for the REST Level-3 API. The resources and workflows are organized
 * into public/secure section and a protected/insecure section. The secure resources
 * are available to the public side of the firewall / DMZ.  The insecure resources
 * are avilable only within the security boundaries.
 * <p/>
 * Target clients for the secure resource are mainly user agents (web browsers) that
 * have a session based security context through CMS and CAS/PubAuth (CAS security is
 * not implemented in this blueprint).
 * <p/>
 * Target clients for insecure resources are backoffice admin tools such as SA2.
 * <p/>
 * Both APIs share a common service layer and entity model but may present different
 * workflow options and resource representations depending on the client type. As
 * an example, a bonus offer may be represented differently
 * for a public client and a protected client. The public client will not have rights
 * to delete a bonus offer definition and so on.
 * <p/>
 * While this doesn't eliminate the need for applying role based security or ACLs, it
 * helps organizing API elements into these two main categories. It also simplifies
 * firewall configuration to only allow external traffic against protected resources.
 *
 */

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = {"com.cityescape.web"})
public class WebConfiguration {

    public WebConfiguration() {
        System.out.println("*************");
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CORSFilter());
        filterRegistrationBean.setName("CORS");

        Map<String,String> initParams=new HashMap<>();
        initParams.put("cors.allowGenericHttpRequests","true");
        initParams.put("cors.allowOrigin", "*");
        initParams.put("cors.supportedHeaders","*");
        initParams.put("cors.supportedMethods", "GET, POST, HEAD, PUT, PATCH, DELETE, OPTIONS");

        filterRegistrationBean.setInitParameters(initParams);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }
}


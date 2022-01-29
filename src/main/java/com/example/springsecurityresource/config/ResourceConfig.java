package com.example.springsecurityresource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 资源Server端
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${yhClient.clientId}")
    private String clientId;
    @Value("${yhClient.clientSecret}")
    private String clientSecret;


    @Primary
    @Bean
    // 用于检查用户传来的token是否正确
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        //设置授权服务器check_token端点完整地址
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        //设置客户端id与secret，注意：client_secret值不能使用passwordEncoder加密！
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //springSecurity默认拦截所有请求,未来拓展的话可以在这里放行请求。
        http.authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    // 配置客户端的resourceId
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("resource_yh").stateless(true);
    }
}
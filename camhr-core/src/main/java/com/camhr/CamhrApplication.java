package com.camhr;


import com.camhr.constants.CamConstantProvider;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import we.business.constant.ConstantProvider;

@EnableSwagger2
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties(CamhrProperties.class)
public class CamhrApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(CamhrApplication.class, args);
  }

  @Value("${version:v1.0.0}")
  private String version;

  @Bean
  public Docket usersDocket() {

    SecurityContext userSecurityContext = SecurityContext
        .builder().securityReferences(defaultAuthorization())
        .forPaths(Predicates.not(Predicates.or(PathSelectors.ant("/" + version + "/*"),
            PathSelectors.ant("/" + version + "/application/**"),
            PathSelectors.ant("/" + version + "/users/authorize")))).build();

    Predicate<String> users = Predicates
        .or(PathSelectors.ant("/" + version + "/users/**"),
            PathSelectors.ant("/" + version + "/application/**"),
            PathSelectors.ant("/" + version + "/*"));

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(ApiInfo.DEFAULT)
        .groupName("USER(SEEKER) API")
        .securitySchemes(Lists.newArrayList(new ApiKey("Authorization", "Authorization", "header")))
        .securityContexts(Lists.newArrayList(userSecurityContext))
        .select().paths(users)
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .build();
  }

  @Bean
  public Docket employersDocket() {

    SecurityContext employerSecurityContext = SecurityContext
        .builder().securityReferences(defaultAuthorization())
        .forPaths(Predicates.not(Predicates.or(PathSelectors.ant("/" + version + "/*"),
            PathSelectors.ant("/" + version + "/application/**"),
            PathSelectors.ant("/" + version + "/employers/authorize")))).build();

    Predicate<String> users = Predicates
        .or(PathSelectors.ant("/" + version + "/employers/**"),
            PathSelectors.ant("/" + version + "/application/**"),
            PathSelectors.ant("/" + version + "/*"));

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(ApiInfo.DEFAULT)
        .groupName("EMPLOYER API")
        .securitySchemes(Lists.newArrayList(new ApiKey("Authorization", "Authorization", "header")))
        .securityContexts(Lists.newArrayList(employerSecurityContext))
        .select().paths(users)
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .build();
  }

  private List<SecurityReference> defaultAuthorization() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
        new SecurityReference("Authorization", authorizationScopes));
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  public ConstantProvider constantProvider() {
    return new CamConstantProvider();
  }
}

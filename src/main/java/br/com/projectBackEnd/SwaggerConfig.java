package br.com.projectBackEnd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	//http://localhost:8080/swagger-ui.html
	//any () , none (), regex () ou ant () 
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.projectBackEnd"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(Collections.singletonList(new ParameterBuilder().name("Authorization")
                .description("Bearer token")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true).build()))
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "API Rest Project USJT",
                "API REST controle de demanda",
                "1.0",
                "Terms of Service",
                new Contact("Nil-One", "nilone.com",
                        "nilone.usjt@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
}
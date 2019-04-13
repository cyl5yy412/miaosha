package com.lnsoft.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类：
 * 在与spring boot集成时，放在与Application.java同级的目录下。
 * 通过@Configuration注解，让Spring来加载该类配置。
 * 再通过@EnableSwagger2注解来启用Swagger2。
 * <p>
 * swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
 * Swagger使用的注解及其说明：
 *
 * @Api：修饰整个类，描述Controller的作用(说明该类的作用。) #
 * <p>
 * @ApiOperation：描述一个类的一个方法，或者说一个接口,注解来给API增加方法说明 #
 * <p>
 * @ApiImplicitParams : 用在方法上包含一组参数说明。
 * @ApiParam：单个参数描述 #
 * <p>
 * @ApiModel：用对象来接收参数 #
 * 描述一个Model的信息（一般用在请求参数无法使用@ApiImplicitParam注解进行描述的时候） l   @ApiModelProperty：描述一个model的属性
 * <p>
 * @ApiProperty：用对象接收参数时，描述对象的一个字段 #
 * <p>
 * @ApiResponse：HTTP响应其中1个描述
 * @ApiResponses：HTTP响应整体描述,用于表示一组响应,用在@ApiResponses中，一般用于表达一个错误的响应信息. #
 * l=   code：数字，例如400
 * l=  message：信息，例如"请求参数没填好"
 * l=   response：抛出异常的类
 * @ApiIgnore：使用该注解忽略这个API #
 * <p>
 * @ApiError ：发生错误返回的信息
 * <p>
 * @ApiParamImplicitL：一个请求参数
 * @ApiParamsImplicit 多个请求参数
 * <p>
 * @ApiImplicitParams : 用在方法上包含一组参数说明。
 * @ApiImplicitParam：用来注解来给方法入参增加说明。 #
 * <p>
 * ###################################################################################
 * <p>
 * 注意：@ApiImplicitParam的参数说明：
 * paramType：指定参数放在哪个地方:5种情况
 * #######(1)header：请求参数放置于Request Header，使用@RequestHeader获取
 * #######(2)query：请求参数放置于请求地址，使用@RequestParam获取
 * #######(3)path：（用于restful接口）-->请求参数的获取：@PathVariable
 * #######(4)body：（不常用）
 * #######(5)form（不常用）
 * name：参数名
 * dataType：参数类型
 * required：参数是否必须传 true | false
 * value：说明参数的意思
 * defaultValue：参数的默认值
 * <p>
 * * <p>
 * 注意：如果方法@RequestMapping未指定method = RequestMethod.DELETE/GET/HEAD/OPTIONS/PATCH/POST/PUT
 * 那么：swagger会自动出现所有的接口（7种）
 * * <p>
 * <p>
 * Created By Chr on 2019/4/10/0010.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //需要指定包结构，而不需要制定到具体的controller类中
//                .apis(RequestHandlerSelectors.basePackage("com.lnsoft.test.sign"))
                .apis(RequestHandlerSelectors.basePackage("com.lnsoft.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * 例如：http://localhost:8090/swagger-ui.html
     *
     * @return
     */

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多请关注http://www.baidu.com")
                .termsOfServiceUrl("http://www.baidu.com")
                .contact("Chr")
                .version("1.0")
                .build();
    }

}

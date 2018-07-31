package com.komatsukat.spring.boot.web.config;

import com.komatsukat.spring.boot.web.interceptor.BaseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription
 * 通过配置，使得spring mvc有能力对静态资源处理
 * 实现WebMvcConfigurationSupport接口会导致spring boot的自动配置失效
 *
 * 想要使用默认配置，无需使用 @EnaleWebMvc 注解。
 * 使用了 @EnableWebMvc 注解后 WebMvcAutoConfiguration 提供的默认配置会失效，必须提供全部配置。
 * @date 14:45 2018/7/31
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public BaseInterceptor getBaseInterceptor() {
        return new BaseInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getBaseInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/*");
    }

    /**
     * 1.添加PathResourceResolver
     * 该resolver的作用是将url为/static/**的请求映射到classpath:/static/
     * 比如请求 http://localhost:8080/static/js/jquery.js时，
     * Spring MVC会查找路径为 classpath:/static/js/js.js的资源文件。
     *
     * 2.指定资源版本号
     * 在请求资源时，加上 /3.1.0 前缀，即 http://localhost:8080/static/js/3.1.0/jquery.js也可正确访问。
     * VersionResourceResolver在处理该请求时，
     * 首先使用PathResourceResolver按照配置的映射关系 “/static/**” => “classpath:/static” 处理，
     * 即查找文件 classpath:/static/js/3.1.0/js.js。
     * 由于该文件不存在，VersionResourceResolver尝试去掉版本号3.1.0，
     * 然后再次查找 classpath:/static/js/js.js，找到文件，直接返回。
     *
     * 3.使用MD5指定版本号
     * 请求资源时，加上资源的md5，即 http://localhost:8080/static/js/jquery-dfbe630979d120fe54a50593f2621225.js 也可正确访问。
     * 由于使用资源的MD5作为版本号，是VersionResourceResolver的其中一种策略，因此与指定版本号的处理方式相同。
     *
     * 4.查找gzip压缩版本
     * EncodedResourceResolver 用来查找资源的压缩版本，它首先使用下一个 Resource Resolver 查找资源，
     * 如果可以找到，则再尝试查找该资源的 gzip 版本。如果存在 gzip 版本则返回 gzip 版本的资源，否则返回非 gzip 版本的资源。
     *
     * 5.resourceChain(true)开启缓存
     * CachingResourceResolver用于缓存其它ResourceResolver查找到的资源。因此 CachingResourceResolver会被放在最外层。
     * 请求先到达 CachingResourceResolver，尝试在缓存中查找，
     * 如果找到，则直接返回，如果找不到，则依次调用后面的 resolver，直到有一个 resolver 能够找到资源，
     * CachingResourceResolver 将找到的资源缓存起来，下次请求同样的资源时，就可以从缓存中取了。
     * 可能有人会担心缓存资源会占用太多的内存。但实际上并没有资源内容，仅仅是对资源的路径（或者说资源的抽象）进行了缓存。
     *
     * 6.省略webjar版本
     * WebJarsResourceResolver并不需要手动添加。WebJarsResourceResolver依赖了webjars-locator包，
     * 因此当添加了webjars-locator依赖时，Spring MVC会自动添加WebJarsResourceResolver。
     * 比如对于请求 http://localhost:8080/static/js/3.1.0/jquery.js 省略版本号 3.1.0
     * 直接使用 http://localhost:8080/static/js/jquery.js 也可访问。
     *
     * 7.Transformer
     * 除了 ResourceResolver，Spring MVC 还支持修改资源内容，即 Resource Transformer。
     * AppCacheManifestTransformer: 帮助处理 HTML5 离线应用的 AppCache 清单内的文件
     * CachingResourceTransformer: 缓存其它 transfomer 的结果，作用同 CachingResourceResolver
     * CssLinkResourceTransformer: 处理 css 文件中的链接，为其加上版本号
     * ResourceTransformerSupport: 抽象类，自定义 transfomer 时继承
     * 比如： CssLinkResourceTransformer。 它会将 css文件中的 @import 或 url() 函数中的资源路径自动转换为包含版本号的路径。
     * 当我们在 style.css 中通过 @import “style-other.css”; 导入了另一个 css 文件，
     * 则 transformer 会自动将该 css 文件路径地址转换为： @import “style-other-d41d8cd98f00b204e9800998ecf8427e.css”。
     *
     * 8.Http 缓存
     * 当请求 /static/style.css 时，返回的头信息中会多两条信息：
     *  Cache-Control:max-age=600, private
     *  Last-Modified:Sun, 04 Oct 2016 15:08:22 GMT
     * 浏览器会将该信息连同资源储存起来，当再次请求该资源时，会取出 Last-Modified 并添加到在请求头 If-Modified-Since 中：
     *  If-Modified-Since:Sun, 04 Oct 2016 15:08:22 GMT
     * Spring MVC在收到请求，发现存在If-Modified-Since，会提取出来该值，并与资源的修改时间比较，
     * 如果发现没有改变，则仅仅返回状态码 304，无需传递资源内容。
     * 浏览器收到状态码 304，明白资源从上次请求到现在未被改变，http 缓存依旧可用。
     *
     * 这里可以通过配置文件实现
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 指定版本号
        /*registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(false)
                .addResolver(new VersionResourceResolver()
                        .addFixedVersionStrategy("3.1.0", "/js/**"));*/

        // 使用MD5指定版本号
        /*registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES).cachePrivate()) //
                .resourceChain(true) // 开启访问路径的缓存
                .addResolver(new EncodedResourceResolver())
                .addResolver(new VersionResourceResolver()
                        .addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());*/
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 设置允许跨域的路径
                .allowedOrigins("*") // 设置允许跨域请求的域名
                .allowCredentials(true) // 是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 设置允许的方法
                .maxAge(3600L);// 跨域允许时间
    }
}
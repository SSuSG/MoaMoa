package SG.MoaMoa;


import SG.MoaMoa.repository.UserRepository;
import SG.MoaMoa.web.argumentresolver.LoginMemberArgumentResolver;
import SG.MoaMoa.web.interceptor.AssociateUserCheckInterceptor;
import SG.MoaMoa.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login", "/logout", "/find/**",
                         "/*.ico", "/error", "/join/**" , "/bootstrap/**"
                );

        registry.addInterceptor(new AssociateUserCheckInterceptor(userRepository))
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login", "/logout", "/find/**",
                        "/*.ico", "/error", "/join/**" , "/bootstrap/**", "/email"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }


}

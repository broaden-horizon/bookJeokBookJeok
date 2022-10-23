package com.kh.bookJeokBookJeok.authentication;

import com.kh.bookJeokBookJeok.authentication.filter.JwtAuthenticationFilter;
import com.kh.bookJeokBookJeok.authentication.filter.JwtVerificationFilter;
import com.kh.bookJeokBookJeok.authentication.handler.MemberAuthenticationFailureHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final AuthenticationUtils authenticationUtils;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin() //같은 오리진에 대하여 프레임 랜더링 허용
                .and()
                .csrf().disable() //개발용이기에 csrf 토큰 검사 안하도록 설정
                .cors() //아래 정의한 corsConfigurationSource 찾아서 적용
                .and()
                //jwt 인가할 때, 매우 짧은 시간 동안만 session 토큰을 SecurityContext에 저장하여 권한 검사하도록 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //커스텀 필터(jwt) 추가한 configurer 추가 적용
                .apply(new JwtFilterConfigurer())
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                // url별 권한 적용
                .authorizeRequests(auth -> auth
                        .antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("ADMIN", "USER")
                        .antMatchers(HttpMethod.GET, "/members").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/members").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/members").hasRole("USER")
                        //WishlistController
                        .antMatchers(HttpMethod.POST, "/wishlist").hasAnyRole("USER", "ADMIN")
                        //Review
                        .antMatchers(HttpMethod.POST, "/review").hasRole("USER")
                        .anyRequest().permitAll()
                );
        return http.build();
    }


    public class JwtFilterConfigurer extends AbstractHttpConfigurer<JwtFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(manager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authenticationUtils);
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

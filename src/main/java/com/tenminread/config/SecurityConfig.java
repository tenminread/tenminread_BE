package com.tenminread.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * 쉼표(,) 구분 허용 오리진 목록.
   * 예) prod: app.cors.allowed-origins: "https://tenminread.vercel.app"
   * dev:  app.cors.allowed-origins: "http://localhost:5173,http://127.0.0.1:5173"
   */
  @Value("${app.cors.allowed-origins:}")
  private String allowedOriginsProp;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        // 프리플라이트는 항상 허용
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // 헬스체크/프로브
        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
        // (선택) 스웨거
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        // 현재 단계: 전체 오픈
        .anyRequest().permitAll()
      );
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();

    // 기본 로컬 포트(개발 편의)
    List<String> defaults = List.of(
      "http://localhost:5173",
      "http://127.0.0.1:5173",
      "http://localhost:3000",
      "http://127.0.0.1:3000"
    );

    List<String> fromProp = (allowedOriginsProp == null || allowedOriginsProp.isBlank())
      ? List.of()
      : Arrays.stream(allowedOriginsProp.split(","))
      .map(String::trim)
      .filter(s -> !s.isEmpty())
      .collect(Collectors.toList());

    // 프로퍼티가 있으면 그것을, 없으면 기본 로컬 도메인 사용
    List<String> allowedOrigins = fromProp.isEmpty() ? defaults : fromProp;

    // ✅ credentials(true)와 호환을 위해 allowedOrigins(정확한 도메인) 사용
    cfg.setAllowedOrigins(allowedOrigins);

    cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setExposedHeaders(List.of("Authorization","Location","X-Total-Count"));
    cfg.setAllowCredentials(true);
    cfg.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
}

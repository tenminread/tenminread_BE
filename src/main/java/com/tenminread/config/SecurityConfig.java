// src/main/java/com/tenminread/config/SecurityConfig.java
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
   * 쉼표(,)로 구분된 허용 오리진 목록을 환경설정에서 주입.
   * 예) application-prod.yml
   * app:
   *   cors:
   *     allowed-origins: "https://tenminread.vercel.app,https://tenminread.vercel.app:5173,http://tenminread.vercel.app:5173,http://localhost:5173,http://127.0.0.1:5173"
   */
  @Value("${app.cors.allowed-origins:}")
  private String allowedOriginsProp;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        // 프리플라이트
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // 헬스체크
        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
        // (선택) 스웨거 오픈
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        // 현재 단계: 전체 오픈
        .anyRequest().permitAll()
      );
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();

    // 프로퍼티가 비었으면 로컬 기본 허용
    List<String> defaults = List.of("http://localhost:*", "http://127.0.0.1:*");
    List<String> fromProp = (allowedOriginsProp == null || allowedOriginsProp.isBlank())
      ? List.of()
      : Arrays.stream(allowedOriginsProp.split(","))
      .map(String::trim)
      .filter(s -> !s.isEmpty())
      .collect(Collectors.toList());

    // 와일드카드 포트/패턴을 허용하려면 Patterns 사용 (credentials 호환)
    cfg.setAllowedOriginPatterns(fromProp.isEmpty() ? defaults : fromProp);

    cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setExposedHeaders(List.of("Authorization","Location","X-Total-Count"));
    cfg.setAllowCredentials(true);
    cfg.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }

  // 별도 CorsFilter 빈 생성 금지 (Security가 위 설정으로 처리합니다)
}

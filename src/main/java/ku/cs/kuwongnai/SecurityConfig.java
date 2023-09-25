package ku.cs.kuwongnai;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Value("${jwt.key}")
  private String jwtKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(requests -> requests
            // .requestMatchers(HttpMethod.GET, "/cart").permitAll()
            .anyRequest().authenticated())

        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwtSpec -> jwtSpec.decoder(jwtDecoder())))
        .build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKey key = new SecretKeySpec(jwtKey.getBytes(), "HMACSHA256");
    return NimbusJwtDecoder.withSecretKey(key).build();
  }
}

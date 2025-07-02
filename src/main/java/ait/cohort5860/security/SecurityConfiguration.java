package ait.cohort5860.security;

import ait.cohort5860.accounting.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomWebSecurity customWebSecurity;

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults()); // Default settings (basic auth - enabled)
        http.csrf(csrf -> csrf.disable()); // Подделываем запрос
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/register", "forum/posts/**", "forum/post/**")
                .permitAll()
                .requestMatchers("/account/user/{login}/role/{role}")
                .hasRole(Role.ADMINISTRATOR.name()
                )
                .requestMatchers(HttpMethod.PATCH, "/account/user/{login}", "/post/{id}/comment/{login}")
                    .access(new WebExpressionAuthorizationManager("#login == authentication.name"))
                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
                    .access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))
                .requestMatchers(HttpMethod.POST, "/forum/post/{login}")
                    .access(new WebExpressionAuthorizationManager("#login == authentication.name"))
                .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}")
                    .access(((authentication, context) -> new AuthorizationDecision(customWebSecurity.checkPostAuthor(Long.valueOf(context.getVariables().get("id")), authentication.get().getName()))))
                .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                    .access((authentication, context) -> {
                        boolean isAuthor = customWebSecurity.checkPostAuthor(Long.valueOf(context.getVariables().get("id")),
                                authentication.get().getName());
                        boolean isModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
                        return new AuthorizationDecision(isAuthor || isModerator);
                })
                .anyRequest()
                    .authenticated());

        return http.build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

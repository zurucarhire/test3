package com.cellulant.iprs.security;

import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.ILoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final ILoginLogService loginLogService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ovveride default springboot '/login' url found in UsernamePasswordAuthenticationFilter because CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userRepository, loginLogService);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // ORDER MATTERS, PUT BELOW CODE ABOVE ALL PERMITS

        http.authorizeRequests().antMatchers("/api/iprs/customer/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");
        http.authorizeRequests().antMatchers("/api/iprs/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");
        http.authorizeRequests().antMatchers("/api/iprs/client/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");

        http.authorizeRequests().antMatchers("/api/iprs/user/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/delete/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/changepassword/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/edituserrole/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/deleteuserrole/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/editaccount/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");

        http.authorizeRequests().antMatchers("/api/iprs/role/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/role/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/role/delete/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/role/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");


        //http.authorizeRequests().antMatchers("/api/iprs/loginlog/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");

        http.authorizeRequests().anyRequest().authenticated();

        //http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

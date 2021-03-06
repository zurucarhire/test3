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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
        http.authorizeRequests().antMatchers("/actuator/**","/swagger-ui/**","/api/psm/user/**","/api/psm/procedure/**"
                ,"/api/psm/question/**","/api/psm/experience/**","/api/psm/misc/**","/api/psm/mpesa/**","/api/psm/newsletter/**"
                ,"/api/psm/merchant/findbyproductid/**","/api/psm/merchant/findall/**","/api/psm/merchant/findallbycategory/**").permitAll();
        http.authorizeRequests().antMatchers("/api/psm/user/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/psm/user/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/psm/user/delete/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/psm/user/findalluserroles/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR","ROLE_CREATOR");
        http.authorizeRequests().antMatchers("/api/psm/user/resetpassword/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/psm/user/updateuserrole/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/psm/user/deleteuserrole/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/psm/user/*").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/psm/merchant/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
//        http.authorizeRequests().antMatchers("/api/psm/merchant/**").hasAnyAuthority("ROLE_CREATOR");

        http.authorizeRequests().antMatchers("/api/iprs/client/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/client/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/client/delete/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/iprs/client/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");

        http.authorizeRequests().antMatchers("/api/iprs/role/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/role/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/role/delete/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/iprs/role/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");

        http.authorizeRequests().antMatchers("/api/iprs/requesttype/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/requesttype/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/requesttype/delete/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/iprs/requesttype/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");

        http.authorizeRequests().antMatchers("/api/iprs/expiryperiod/create/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/expiryperiod/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");
        http.authorizeRequests().antMatchers("/api/iprs/expiryperiod/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");

        http.authorizeRequests().antMatchers("/api/iprs/loginlog/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");
        http.authorizeRequests().antMatchers("/api/iprs/search/find/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CREATOR","ROLE_EDITOR","ROLE_USER");
        http.authorizeRequests().antMatchers("/api/iprs/search/requests/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR");

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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/h2-console/**",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}

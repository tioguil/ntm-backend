package br.com.projectBackEnd.Utili;

import br.com.projectBackEnd.service.CunstonUsuarioDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import static br.com.projectBackEnd.Utili.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
public class SercurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CunstonUsuarioDetailService cunstonUsuarioDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
                .antMatchers("/*/avaliador/*").hasRole("avaliador")
                .antMatchers("/*/gestor/*").hasRole("gestor")
                .antMatchers("/*/adm/*").hasRole("adm")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                 .addFilter(new JWTAuthorizationFilter(authenticationManager(), cunstonUsuarioDetailService));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cunstonUsuarioDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("guil").password("{noop}123").roles("USER").and()
//        .withUser("admin").password("{noop}123").roles("USER", "ADMIN");
//    }

}

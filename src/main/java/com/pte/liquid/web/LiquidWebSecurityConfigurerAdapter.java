package com.pte.liquid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class LiquidWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
	
	
	@Value("${liquid.master.username}")
	private String username;
	
	@Value("${liquid.master.password}")
	private String password;
	
	@Autowired
    private LiquidAuthenticationEntryPoint authenticationEntryPoint;
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser(username).password(password)
          .authorities("ROLE_USER");
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//disable csrf for h2 console
    	http.csrf().disable();

        //avoid blank h2 console page
        http.headers().frameOptions().disable();
    	
    	//auth everything
        http.authorizeRequests().antMatchers("/*")
    	   .permitAll()
    	  .anyRequest().authenticated()
          .and()
          .httpBasic()
          .authenticationEntryPoint(authenticationEntryPoint);

        
        http.addFilterAfter(new LiquidFilter(),
          BasicAuthenticationFilter.class);
    }
}

package com.smartgigInternal.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;



@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 
		auth.userDetailsService(userDetailsServiceImpl);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		List<String> corsHeaders = new ArrayList<>();
 		corsHeaders.add("Authorization");
 		corsHeaders.add("Cache-Control");
 		corsHeaders.add("Content-Type");
		corsHeaders.add("Access-Control-Allow-Origin");
		corsHeaders.add("Accept");
		corsHeaders.add("Origin");
		corsHeaders.add("X-Requested-With");
		corsHeaders.add("Access-Control-Request-Headers");
		corsHeaders.add("Access-Control-Request-Method");

 		List<String> originsHeaders = new ArrayList<>();
 		originsHeaders.add("http://13.232.116.42");//new test
 		originsHeaders.add("http://3.108.72.42");	//new prod
 		originsHeaders.add("http://localhost:3000");
 		originsHeaders.add("http://ngriti.com");

 		List<String> methodsHeaders = new ArrayList<>();
 		methodsHeaders.add("GET");
 		methodsHeaders.add("POST");
 		methodsHeaders.add("PATCH");
 		methodsHeaders.add("PUT");
 		methodsHeaders.add("DELETE");
 		methodsHeaders.add("OPTIONS");

 		List<String> exposedHeaders = new ArrayList<>();
 		exposedHeaders.add("Authorization");

        CorsConfiguration  corsConfiguration=new CorsConfiguration();
		
        corsConfiguration.setAllowedHeaders(corsHeaders);
 		corsConfiguration.setAllowedOrigins(originsHeaders);
		
 		corsConfiguration.setAllowedMethods(methodsHeaders);

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setExposedHeaders(exposedHeaders);
		
		http.csrf().and().cors().disable().formLogin();	
		http.csrf().disable().formLogin();
		http.authorizeRequests().antMatchers("/smg/admin/adminLogin",
				//"/smg/**",
				"/smg/download/employee-csv",
				"/v2/api-docs", 
				"/swagger-resources/configuration/ui",
				"/swagger-resources",
				"/swagger-resources/configuration/security", 
				"/swagger-ui.html", "/webjars/**").permitAll()
		
		.antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated() .and().csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
		.exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);		     
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	AuthenticationManager authentication() throws Exception {
		return super.authenticationManagerBean();
	}
}
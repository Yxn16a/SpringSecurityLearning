package com.example.springsecuritylearning.security;

import com.example.springsecuritylearning.auth.ApplicationUserService;
import com.example.springsecuritylearning.jwt.JwtUserNameAndPasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.springsecuritylearning.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // to make sure the method authorization is seen and used
// during the application run
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // to  matchers order  matters therefore list them as Delete,Post,put, get
                //csrf(cross site request forgery protect forgery)
                // csrf is good for token
                //  .csrf().disable()  spring security stops forgery but when disables then no token
                // is sent
                // use csrf when you are not using browser

                // configure token
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()// because we are using postman
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwts have to be stateless
                .and()
                .addFilter(new JwtUserNameAndPasswordAuthenticationFilter(authenticationManager()))  // this is how we add a filter
                .authorizeRequests()
                .antMatchers("/","index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                // these were replaced by annotation on the methods of @authorize
//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMINTRAINEE.name(),ADMIN.name())
                .anyRequest()
                // each request has to be authenticated with username and password
                .authenticated();


//                .and()
//                //.httpBasic();
//                .formLogin() // this changes from basics to form login
//                    .loginPage("/login")
//                    .permitAll()
//                    .defaultSuccessUrl("/courses",true)
//                    .passwordParameter("password")
//                    .usernameParameter("username")
//                .and()
//                .rememberMe()// default to 2 weeks
//                        .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
//                        .key("somethingverysecured") // this extend sessions to 21 days
//                        .rememberMeParameter("remember-me") // this come from the form login
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID","remember-me")
//                .logoutSuccessUrl("/login"); // logout must be a post methodes
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // this is how we return data from the inmemory database
    //THE FOLLOWING METHOD IS NOT NEEDED BECAUSE WE IMPLEMENTED OURS IN
    //FAKEAPPLICATIONUSERDao
//    @Override
//    @Bean
//    // this is a role based authentication
//    protected UserDetailsService userDetailsService() {
//        UserDetails annaSmithUser = User.builder()
//                .username("annasmith")
//                .password(passwordEncoder.encode("password")) // role based
//                 // authentication
////                .roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//        // create admin
//        UserDetails lindUser = User.builder()
//                .username("linda")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails TomUser = User.builder()
//                .username("tom")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ADMINTRAINEE.name()) //
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                annaSmithUser,
//                lindUser,
//                TomUser
//        );
//    }
}

package tacos.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
   @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }
   
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

       httpSecurity
              .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
               	.requestMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
               	.requestMatchers("/design", "/orders/**").permitAll() // 로그인 api
               		//.access("hasRole('ROLE_USER')")
               	.requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll() // 회원가입 api
               	.requestMatchers("/**").permitAll()

              .and()
               	.formLogin()
               		.loginPage("/login")
               
              .and()
               	.httpBasic()
               		.realmName("Taco Cloud")
               
              .and()
               	.logout()
               	.logoutSuccessUrl("/")
                 
              .and()
               	.csrf()
               		.ignoringRequestMatchers("/ingredients/**", "/design", "/orders/**")

               // Allow pages to be loaded in frames from the same origin; needed for H2-Console
              .and()  
               	.headers()
               		.frameOptions()
               			.sameOrigin();               
               
       return httpSecurity.build();
   }
   
   @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
   		throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
   }

}



//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  
//  @Autowired
//  private UserDetailsService userDetailsService;
//  
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//	  
//    http
//      .authorizeRequests()
//        .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
//        .antMatchers("/design", "/orders/**").permitAll()
//            //.access("hasRole('ROLE_USER')")
//        .antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
//        .antMatchers("/**").access("permitAll")
//        
//      .and()
//        .formLogin()
//          .loginPage("/login")
//          
//      .and()
//        .httpBasic()
//          .realmName("Taco Cloud")
//          
//      .and()
//        .logout()
//          .logoutSuccessUrl("/")
//          
//      .and()
//        .csrf()
//          .ignoringAntMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**")
//
//      // Allow pages to be loaded in frames from the same origin; needed for H2-Console
//      .and()  
//        .headers()
//          .frameOptions()
//            .sameOrigin()
//      ;
//  }
//
//  @Bean
//  public PasswordEncoder encoder() {
////    return new StandardPasswordEncoder("53cr3t");
//    return NoOpPasswordEncoder.getInstance();
//  }
//  
//  
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//
//    auth
//      .userDetailsService(userDetailsService)
//      .passwordEncoder(encoder());
//    
//  }
//
//}

package za.co.bakery.app.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import za.co.bakery.backend.data.entity.User;
import za.co.bakery.backend.repository.UserRepository;
import za.co.bakery.ui.views.login.LoginView;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/robots.txt"),
                new AntPathRequestMatcher("/icons/**"),
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/h2-console/**")
        );
    }

    public CurrentUser currentUser(UserRepository userRepository){
        final String username = SecurityUtils.getUsername();
        User user = username != null ? userRepository.findByEmailIgnoreCase(username) : null;
        return  () -> user;
    }
}

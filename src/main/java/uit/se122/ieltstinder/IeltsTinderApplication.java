package uit.se122.ieltstinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uit.se122.ieltstinder.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class IeltsTinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeltsTinderApplication.class, args);
    }

}

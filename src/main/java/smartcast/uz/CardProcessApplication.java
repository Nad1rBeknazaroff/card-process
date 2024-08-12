package smartcast.uz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import smartcast.uz.repository.impl.BaseRepositoryImpl;

@SpringBootApplication

@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CardProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardProcessApplication.class, args);
    }

}

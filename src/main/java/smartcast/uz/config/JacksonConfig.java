package smartcast.uz.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Customize the ObjectMapper as needed
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Add more custom configurations here
        return mapper;
    }
}


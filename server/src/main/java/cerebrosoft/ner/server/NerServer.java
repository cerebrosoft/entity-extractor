package cerebrosoft.ner.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NerServer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NerServer.class, args);
    }
}

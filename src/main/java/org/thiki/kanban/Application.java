package org.thiki.kanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by xubitao on 04/26/16.
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationPidFileWriter("thiki-kanban.pid"));
        springApplication.run(args);
    }
}

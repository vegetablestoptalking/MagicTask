package com.vgtstptlk.magictask;

import com.vgtstptlk.magictask.domain.Task;
import com.vgtstptlk.magictask.domain.User;
import com.vgtstptlk.magictask.repos.TaskRepository;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@Configuration
public class MagicTaskApplication {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           TaskRepository taskRepository) {
        User user = userRepository.save(new User("mag",
                new BCryptPasswordEncoder().encode("password"), "Magerram", "Zeynalov"));
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong,a,a,a,a,a,a,a,a,,a,a,a,a".split(","))
                .forEach(
                        a -> {

                            taskRepository.save(new Task(user,
                                    "Look" + a, "A description"));
                            taskRepository.save(new Task(user,
                                    "Say" + a, "A description"));
                        });
    }
    public static void main(String[] args) {
        SpringApplication.run(MagicTaskApplication.class, args);
    }

}

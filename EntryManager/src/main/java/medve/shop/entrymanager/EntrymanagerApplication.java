package medve.shop.entrymanager;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EntrymanagerApplication {


    public static void main(String[] args) {
        SpringApplication.run(EntrymanagerApplication.class, args);
    }


}

package cn.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName:LeeEurekaApplication
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 11:25
 * @Description: TODO
 */
@SpringBootApplication
@EnableEurekaServer
public class LeeEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeeEurekaApplication.class);
    }
}

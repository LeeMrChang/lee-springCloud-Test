package cn.lee.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy  //启用zuul网关组件
public class LeeZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeeZuulApplication.class, args);
    }

}

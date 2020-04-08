package cn.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName:LeeServiceConsumerApplication
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 11:01
 * @Description: TODO
 */
//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker  //开启熔断器
@SpringCloudApplication  //组合注解，非常强大
@EnableFeignClients  //启用feign组件
public class LeeServiceConsumerApplication {
    /**
     * 远程注入与调用
     * 使用@LoadBalanced 开启负载均衡。现在是消费方，一个消费方可能对应多个服务提供者
     */
    @Bean
    @LoadBalanced  //开启负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LeeServiceConsumerApplication.class);
    }
}

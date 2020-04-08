package cn.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName:LeeServiceProviderApplication
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 10:43
 * @Description: TODO
 */

@SpringBootApplication
@MapperScan("cn.lee.mapper")
@EnableDiscoveryClient
public class LeeServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeeServiceProviderApplication.class);
    }
}

package cn.lee.controller;


import cn.lee.client.UserClient;
import cn.lee.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ClassName:UserController
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 11:06
 * @Description: TODO
 */
@RestController
@RequestMapping("consumer/user")
//@DefaultProperties(defaultFallback = "queryUserByIdFallBack")  //定义全局的熔断方法
public class UserController {


    /**
     * 使用feign接口
     */
    @Autowired
    private UserClient userClient;

   /* @Autowired
    private RestTemplate restTemplate;

    *//**
     * 获取所有服务实例
     *//*
    @Autowired
    private DiscoveryClient discoveryClient;*/

    @GetMapping
    //表示在调用此服务器方法太多时，失败时调用一下关联到的熔断方法，触发熔断
    @HystrixCommand //声明关联熔断方法
    public String queryUserById(@RequestParam("id") Integer id){
        /*List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
        ServiceInstance instance = instances.get(0);*/
        /**
         * 使用了@LoadBalanced  //开启负载均衡
         * 直接启用springCloud的负载均衡，访问服务器名称即可。假设有多个服务，会自动分析端口
         */
//        return this.restTemplate.getForObject("http://service-provider/user/"+id,String.class);
        return this.userClient.queryUserById(id)+"";
    }

    /**
     * 设置熔断方法
     */
    public String queryUserByIdFallBack(){
        return "服务器正忙，请求稍后再试！";
    }
}

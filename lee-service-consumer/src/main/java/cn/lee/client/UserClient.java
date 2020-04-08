package cn.lee.client;

import cn.lee.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName:UserClient
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 21:21
 * @Description: TODO
 */
//声明这是一个feign接口,指定服务的id,fallback是指定此个接口服务的熔断方法
@FeignClient(value = "service-provider",fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping("user/{id}")
    public User queryUserById(@PathVariable("id") Integer id);
}

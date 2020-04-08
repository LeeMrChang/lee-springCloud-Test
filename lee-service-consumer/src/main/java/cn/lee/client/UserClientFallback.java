package cn.lee.client;

import cn.lee.pojo.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName:UserClientFallback
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 21:48
 * @Description: TODO
 */
@Component
public class UserClientFallback implements UserClient {
    /**
     * feign 组件 的 熔断方法
     * @param id
     * @return
     */
    @Override
    public User queryUserById(Integer id) {
        User user = new User();
        user.setUsername("服务器正忙，请稍后再试！");
        return user;
    }
}

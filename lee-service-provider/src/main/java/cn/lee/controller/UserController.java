package cn.lee.controller;

import cn.lee.pojo.User;
import cn.lee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:UserController
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 10:50
 * @Description: TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User queryUserById(@PathVariable("id")Integer id){
        return this.userService.queryUserById(id);
    }
}

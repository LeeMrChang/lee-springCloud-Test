package cn.lee.service;

import cn.lee.mapper.UserMapper;
import cn.lee.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName:UserService
 * @Author：Mr.lee
 * @DATE：2020/04/07
 * @TIME： 10:48
 * @Description: TODO
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserById(Integer id){
        return this.userMapper.selectByPrimaryKey(id);
    }
}

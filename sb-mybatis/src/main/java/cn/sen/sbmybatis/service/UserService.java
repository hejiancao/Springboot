package cn.sen.sbmybatis.service;

import cn.sen.sbmybatis.mapper.UserMapper;
import cn.sen.sbmybatis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> listUser() {
        return userMapper.listUser();
    }
}

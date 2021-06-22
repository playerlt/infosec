package com.index.dao;


import java.util.List;

import com.index.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * User 访问接口
 * 由Mybatis机制－UserMapper.xml配置文件实现
 * 
 * 
 * @author infosec
 *
 */
@Component("userMapper")
public interface UserMapper {
	
	/**
	 * 获取所有系统用户
	 * 
	 * @return 
	 */
	public List<User> listUsers();
	
	
	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(@Param(value = "id") String id);

	User getUserByUserName(String username);

}

package com.index.controller;

import java.util.List;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.index.dao.UserMapper;
import com.index.model.User;
import com.index.security.WeChatAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Controller 业务接口
 * ‘@RequestMapping("/users")’用于定义资源的路径
 *  比如：http://localhost:8080/infosec/users指向的资源为UserController的listUser函数
 *  
 * @author infosec
 *
 */
@Controller(value = "userController")
public class UserController {

	String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoYmAJFNJlmpMgK9hFRsMOFH9osOybRQ8fkUgnRVk3a9GVKCXQL6WRY+rQDlmv7s5irhzjHeVZ2qy+iaRETMDoGvWIm7s2jk+LB2+4m30hBDrnOFcjzWeZjuRSqKEPeaInyN8SbPiLSVmcjG5HkrtHLJHcqjl2ei9t8hjTgj4+2wIDAQAB";
	String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAo" +
			"GBAKhiYAkU0mWakyAr2EVGww4Uf2iw7JtFDx+RSCdFWTdr0ZUoJdAvpZFj6tAOWa/uzmKu" +
			"HOMd5VnarL6JpERMwOga9YibuzaOT4sHb7ibfSEEOuc4VyPNZ5mO5FKooQ95oifI3xJ" +
			"s+ItJWZyMbkeSu0cskdyqOXZ6L23yGNOCPj7bAgMBAAECgYAozZXp/XKyjk+Cp" +
			"z1EJE6V9d+d9emQAp8UjjcG1SY2aNkjpNPs6+7aGXVIL4IKQPsgLILxgkgWiwf7MA1v" +
			"Q44nCcVfWiGNKNHy2DJwVH/+czy7N4TUaYiGW6sZGyh7KWbHSQAWHVPszWcg" +
			"9E7E0Y6FGTfsdL9ghuNG57B99uUleQJBAP8cfFC9PiN+TG4JU71sy7D6AVgwjBn50b" +
			"VtRg+ZkIHfSv62tBGyDnjgVlihiGgLuZT+AbShjzMw6pb5e90eQP8CQQCo+ItXgK" +
			"6Q6CwjvH97fEXczTbbnsy6v48B82r1oOuBQ8teHb5kZgg2wWvNkmgldRug8V8+lkE42BYtLKF3xyYlAkAip/T/ZuX1Hmg1npDAr/Hv5" +
			"dae62Fs+fISKnkVD3CBJBtlBN7rdHvg0eEJA1CricQ5SFRk/Hmeo6uKvPOls0FzAkAA+3jr6E6bfw4KoyTmleFeGD9SZYjxKP3u1/huNyJHXRqIkImz0bgIgXVb+5bpaNXhSKXyGjO" +
			"E3hS67IB/zsOVAkAtn5wmxuoqAHkC+HODXaonIBptckTrMQc3t+hnFsLN8dbGuplTYCu7pQHFeYE5zkOR7+8VsOvMmUoadm0hqdBR";

	/**
	 *  Spring 控制反转与依赖注入机制
	 */
	@Autowired
	private UserMapper userMapper;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager manager;

	@RequestMapping(value = "/weChat/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("loginBean") User user, Model model){
//		model.addAttribute("loginBean", new User());
		String encryptUsername = encrypt(user.getUsername());
		try {
			manager.authenticate(new WeChatAuthenticationToken(encryptUsername,PUBLIC_KEY));
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User user = userMapper.getUser("5038");
//		model.addAttribute("loginBean", user);
		return "../../index";
	}


	/**
	 * 获取所有用户
	 * 
	 * @param model (Spring MVC会为程序初始化model对象，运行时注入)
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(ModelMap model){
		
		try{
			// 从数据库获取所有用户
			List<User> users = userMapper.listUsers();
			
			// 将数据model中，该model有Spring MVC框架维护
			// 在整个request生命周前内有效（下文user_list_servlet中有效）
			model.addAttribute("users", users);
			
			// 对应 /pages/user_list.jsp 对应的页面servlet (user_list_servlet)
			// 即当前request执行转至user_list_servlet执行
			// user_list_servlet利用model中的数据，生成html视图，并返回浏览器该html文件
			return "user_list";
			
		}catch(Exception ex){
			// 如果有任何错误，返回系统错误页面 error.jsp
			// 正式系统中，应该返回更详细的错误信息，帮助用户诊断并采取合适措施
			return "error";
		}

	}
	
	/**
	 * 获取用户详细信息
	 * 
	 * @param model 注释见上文
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUser(ModelMap model){
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User user = userMapper.getUserByUserName(username);
			
			model.addAttribute("user", user);
			
			return "user_detail";
			
		}catch(Exception ex){
			return "error";
		}

	}

	@RequestMapping(value="/forelogin", method=RequestMethod.GET)
	public String initForm(ModelMap model){
		User loginBean = new User(); //用于转换到form表单的对象
		model.addAttribute("loginBean", loginBean);
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:../../index.jsp";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	
	// getter and setters
	
	public UserMapper getUserMapper() {
		return userMapper;
	}


	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public String encrypt(String code){
		RSA rsa = new RSA(PRIVATE_KEY, null);
		byte[] encrypt = rsa.encrypt(StrUtil.bytes(code, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
		return Base64.encode(encrypt);
	}


}

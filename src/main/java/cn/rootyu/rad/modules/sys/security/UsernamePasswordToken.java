package cn.rootyu.rad.modules.sys.security;

/**
 * 用户和密码令牌类
 * @author yuhui
 * @version 1.0
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	//private String captcha;//预留验证码
	
	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
	}

	
}
package cloud.simple.service.web;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import cloud.simple.service.contants.Constant;
import cloud.simple.service.domain.SysAdminMenuService;
import cloud.simple.service.domain.SysAdminRuleService;
import cloud.simple.service.domain.SysAdminUserService;
import cloud.simple.service.model.SysAdminRule;
import cloud.simple.service.model.SysAdminUser;
import cloud.simple.service.util.EncryptUtil;
import cloud.simple.service.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 登录控制层
 * @author leo.aqing
 *
 */
@RestController
@RequestMapping("/admin")
@Api(value = "LoginController", description="登录接口")
public class LoginController extends CommonController{
	@Autowired
	private SysAdminUserService sysAdminUserService;
	@Autowired
	private SysAdminRuleService sysAdminRuleService;
	@Autowired
	private SysAdminMenuService sysAdminMenuService;
	@Autowired
	private DefaultKaptcha captchaProducer;
	
	/**
	 * 登录
	 * @param record
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "登录", notes = "登录")
	@ApiImplicitParams({@ApiImplicitParam(name = "record", required=true, dataType = "SysAdminUser")	})
	@PostMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
	public String login(@RequestBody SysAdminUser record,HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		if(StringUtils.isBlank(record.getUsername())) {
			return FastJsonUtils.resultError(-100, "账号不能为空", null);
		}
		record.setPassword(DigestUtils.md5Hex(record.getPassword()));
		SysAdminUser adminUser = sysAdminUserService.selectOne(record);
		if(adminUser == null) {
			return FastJsonUtils.resultError(-100, "帐号与密码错误不正确", null);
		}
		if(!adminUser.getStatus().equals(Byte.valueOf("1"))) {
			return FastJsonUtils.resultError(-100, "帐号已被禁用", null);
		}
		String authKey = EncryptUtil.encryptBase64(adminUser.getUsername()+"|"+adminUser.getPassword(), Constant.SECRET_KEY);
		// 返回信息
		data.put("rememberKey", authKey);
		data.put("authKey", authKey);
		data.put("sessionId", request.getSession().getId());
		data.put("userInfo", adminUser);
		List<SysAdminRule> rulesTreeList = sysAdminRuleService.getTreeRuleByUserId(adminUser.getId());
		List<String> rulesList = sysAdminRuleService.rulesDeal(rulesTreeList);
		data.put("rulesList", rulesList);
		data.put("menusList", sysAdminMenuService.getTreeMenuByUserId(adminUser.getId()));
		
		return FastJsonUtils.resultSuccess(200, "登录成功", data);
	}
	
	
	/**
	 * 重新登录
	 * @param rememberKey
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "重新登录", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rememberKey", value ="登录成功后的授权码", required = true, dataType = "String")
	})
	@RequestMapping(value = "/relogin", produces = {"application/json;charset=UTF-8"})
	public String relogin(String rememberKey,HttpServletRequest request) {
		String rememberValue = EncryptUtil.decryptBase64(rememberKey, Constant.SECRET_KEY);
		String[] splits = rememberValue.split("|");
		SysAdminUser record = new SysAdminUser();
		record.setUsername(splits[0]);
		record.setUsername(splits[1]);
		SysAdminUser user = sysAdminUserService.selectOne(record);
		if(user == null) {
			return FastJsonUtils.resultError(-400, "重新登录失败", null);
		}
		return FastJsonUtils.resultSuccess(200, "重新登录成功", null);
	}
	
	/**
	 * 登出
	 * @param session
	 * @return
	 */
	@ApiOperation(value = "登出", notes = "")
	@PostMapping(value = "/logout", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String logout(HttpSession session){
		session.invalidate();
		return FastJsonUtils.resultSuccess(200, "退出成功", null);
	}
	
	/***
	 * 验证码
	 */
	@ApiOperation(value = "验证码", notes = "")
	@GetMapping(value = "/verify")
	public void verify(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control",  
                "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
  
        String capText = captchaProducer.createText();  
        System.out.println("capText: " + capText);  
  
        try {  
            String uuid=UUID.randomUUID().toString();              
            //redisTemplate.opsForValue().set(uuid, capText,60*5,TimeUnit.SECONDS);  
            Cookie cookie = new Cookie("captchaCode",uuid);  
            response.addCookie(cookie);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
	}
	
	/**
	 * 修改密码
	 * @param old_pwd
	 * @param new_pwd
	 * @param session
	 */
	@PostMapping(value = "/setInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(value = "修改密码", notes = "")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "old_pwd", value ="旧密码", required = true, dataType = "String"),
		@ApiImplicitParam(name = "new_pwd", value ="新密码", required = true, dataType = "String")
	})
	public String setInfo(String old_pwd, String new_pwd){
		return sysAdminUserService.setInfo(this.getCurrentUser(),old_pwd, new_pwd);
	}
}

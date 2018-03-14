package com.github.liuweijw.system.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuweijw
 */
@Configuration
@ConfigurationProperties(prefix = "fw.auth")
public class AuthServerConfiguration {
	
	/**
	 * 客户端id
	 */
    private String clientId;
    
    /**
     * 客户端密码
     */
    private String clientSecret;
    
    /**
     * scope
     */
    private String scope;
    
    /**
     * RSA private 密匙
     */
    private String privateKey;
    
    /**
     * RSA public 公匙
     */
    private String publicKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
    
}

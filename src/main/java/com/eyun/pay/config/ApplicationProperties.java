package com.eyun.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Pay.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private final Pay pay = new Pay();
	
	public Pay getPay() {
		return pay;
	}

	public static class Pay {
		
		public final Alipay alipay = new Alipay();
		
		public Alipay getAlipay() {
			return alipay;
		}


		public static class Alipay {
			
			private String appid;
			
			private String appPrivateKey;
			
			private String appPublicKey;
			
			private String aesKey;
			
			private String domainName;

			public String getDomainName() {
				return domainName;
			}

			public void setDomainName(String domainName) {
				this.domainName = domainName;
			}

			public String getAesKey() {
				return aesKey;
			}

			public void setAesKey(String aesKey) {
				this.aesKey = aesKey;
			}

			public String getAppid() {
				return appid;
			}

			public String getAppPrivateKey() {
				return appPrivateKey;
			}

			public String getAppPublicKey() {
				return appPublicKey;
			}

			public void setAppid(String appid) {
				this.appid = appid;
			}

			public void setAppPrivateKey(String appPrivateKey) {
				this.appPrivateKey = appPrivateKey;
			}

			public void setAppPublicKey(String appPublicKey) {
				this.appPublicKey = appPublicKey;
			}
			
		}
		
	}
	
}

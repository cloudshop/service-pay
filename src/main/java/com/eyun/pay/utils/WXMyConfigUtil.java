package com.eyun.pay.utils;

import com.github.wxpay.sdk.WXPayConfig;  
  
import java.io.ByteArrayInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStream;  
  
public class WXMyConfigUtil implements WXPayConfig {  
    private byte[] certData;  
  
    public WXMyConfigUtil() throws Exception {  
        String certPath = "cert/apiclient_cert.pem";//从微信商户平台下载的安全证书存放的目录  
  
        File file = new File(certPath);  
        InputStream certStream = new FileInputStream(file);  
        this.certData = new byte[(int) file.length()];  
        certStream.read(this.certData);  
        certStream.close();  
    }  
  
    @Override  
    public String getAppID() {  
        return "wxf177c6755716fa32";  
    }  
  
    //parnerid  
    @Override  
    public String getMchID() {  
        return "1500998061";  
    }  
  
    @Override  
    public String getKey() {  
        return "6H7vSZjhOQEhjsCVA9b2XKqjooTWBVZr";  
    }  
  
    @Override  
    public InputStream getCertStream() {  
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);  
        return certBis;  
    }  
  
    @Override  
    public int getHttpConnectTimeoutMs() {  
        return 8000;  
    }  
  
    @Override  
    public int getHttpReadTimeoutMs() {  
        return 10000;  
    }  
}
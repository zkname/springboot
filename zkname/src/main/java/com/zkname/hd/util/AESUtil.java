package com.zkname.hd.util;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.LoggerFactory;

public class AESUtil {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * AES 加密操作
	 *
	 * @param content
	 *            待加密内容
	 * @param password
	 *            加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器
			byte[] result = cipher.doFinal(byteContent);// 加密
			return Base64.encodeBase64String(result);// 通过Base64转码返回
		} catch (Exception ex) {
			LoggerFactory.getLogger(AESUtil.class).error("encrypt", ex);
		}
		return null;
	}

	/**
	 * AES 解密操作
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content, String password) {
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
			// 执行操作
			byte[] result = cipher.doFinal(Base64.decodeBase64(content));
			return new String(result, "utf-8");
		} catch (Exception ex) {
			LoggerFactory.getLogger(AESUtil.class).error("decrypt", ex);
		}
		return null;
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		// 返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			// AES 要求密钥长度为 128
			kg.init(128, new SecureRandom(password.getBytes()));
			// 生成一个密钥
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
		} catch (NoSuchAlgorithmException ex) {
			LoggerFactory.getLogger(AESUtil.class).error("getSecretKey", ex);
		}
		return null;
	}

	
    /** 
     * AES解密 
     * @param content 密文 
     * @return 
     * @throws InvalidAlgorithmParameterException 
     * @throws NoSuchProviderException 
     */  
    public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte){
        try {  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");  
            Key sKeySpec = new SecretKeySpec(keyByte, KEY_ALGORITHM);
            AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);  
            params.init(new IvParameterSpec(ivByte));  
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);// 初始化  
            byte[] result = cipher.doFinal(content);
            return result;  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }catch (Exception e) {
            e.printStackTrace();  
        }  
        return null;  
    }
    
    
    public static String decrypt(String content, String keyByte, String ivByte) throws UnsupportedEncodingException{
    	 return new String(decrypt(content.getBytes("UTF-8"), keyByte.getBytes("UTF-8"), ivByte.getBytes("UTF-8"))); 
    }
	
	public static void main(String[] args) {
		String s = "hello,您好";
		System.out.println("s:" + s);
		String s1 = AESUtil.encrypt(s, "1231231321231321313231321313fd 发多少房贷单方事故费工费时广东佛山发34 还要退款楼里看电视发多少d2scfdsfds4");
		System.out.println("s1:" + s1);
		System.out.println("s2:" + AESUtil.decrypt(s1, "1231231321231321313231321313fd 发多少房贷单方事故费工费时广东佛山发34 还要退款楼里看电视发多少d2scfdsfds4"));

	}

}

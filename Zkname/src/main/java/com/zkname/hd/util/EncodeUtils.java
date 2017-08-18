package com.zkname.hd.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.zkname.hd.util.exception.ExceptionUtils;

/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * 
 */
public class EncodeUtils {
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String ALPHABET_34 = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static final String ALPHABET_32 = "HJK01234ABCEFGLMNPQRSWXY56789TUV";
	private static final String ALPHABET_11 = "95A2KB1C687F";

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * Hex编码, byte[]->String.
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码, String->byte[].
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码, byte[]->String.
	 */
	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static String encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码, String->byte[].
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base62(0_9A_Za_z)编码数字, long->String.
	 */
	public static String encodeBase62(long num) {
		return alphabetEncode(num, 62,ALPHABET);
	}

	/**
	 * Base62(0_9A_Za_z)解码数字, String->long.
	 */
	public static long decodeBase62(String str) {
		return alphabetDecode(str, 62,ALPHABET);
	}
	
	/**
	 * Base36(0_9a_z)编码数字, long->String.
	 */
	public static String encodeBase11(long num) {
		return alphabetEncode(num, 11,ALPHABET_11);
	}

	/**
	 * Base36(0_9a_z)解码数字, String->long.
	 */
	public static long decodeBase11(String str) {
		return alphabetDecode(str, 11,ALPHABET_11);
	}
	
	/**
	 * Base36(0_9a_z)编码数字, long->String.
	 */
	public static String encodeBase32(long num) {
		return alphabetEncode(num, 32,ALPHABET_32);
	}

	/**
	 * Base36(0_9a_z)解码数字, String->long.
	 */
	public static long decodeBase32(String str) {
		return alphabetDecode(str, 32,ALPHABET_32);
	}
	
	/**
	 * Base36(0_9a_z)编码数字, long->String.
	 */
	public static String encodeBase34(long num) {
		return alphabetEncode(num, 34,ALPHABET_34);
	}

	/**
	 * Base36(0_9a_z)解码数字, String->long.
	 */
	public static long decodeBase34(String str) {
		return alphabetDecode(str, 34,ALPHABET_34);
	}
	
	

	private static String alphabetEncode(long num, int base,String alphabet) {
		num = Math.abs(num);
		StringBuilder sb = new StringBuilder();
		for (; num > 0; num /= base) {
			sb.append(alphabet.charAt((int) (num % base)));
		}

		return sb.toString();
	}

	private static long alphabetDecode(String str, int base,String alphabet) {
		long result = 0;
		for (int i = 0; i < str.length(); i++) {
			result += new BigDecimal(alphabet.indexOf(str.charAt(i))).multiply(new BigDecimal(Math.pow(base, i))).longValue();
		}
		return result;
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml11(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		Hasher hasher = Hashing.md5().newHasher();  
		hasher.putString(str,Charset.forName("UTF-8")); 
		return hasher.hash().toString();
	}
	
	public static String sha1(String str) {
		Hasher hasher = Hashing.sha1().newHasher();  
		hasher.putString(str,Charset.forName("UTF-8")); 
		return hasher.hash().toString();
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String getMac() {
        int bound = 2;
        String result = EncodeUtils.md5(getUUID()).substring(0, 12);
        String tmp = "";
        for(int i = 0; i < 12/bound; i ++) {
            tmp += result.substring(i*2, i*2+2) + ":"; 
        }
        return tmp.substring(0,tmp.length() - 1);
    }
	
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return encodeBase64(aesEncryptToBytes(content, encryptKey));  
    }  
	
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));  
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
    
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(decodeBase64(encryptStr), decryptKey);  
    }  
    
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
          
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
    } 
	
	
	
	public static void main(String[] args) throws Exception {
//		System.out.println(String.format("%03d",RandomUtils.nextLong(0, 999)));
		
//		for(int i=0;i<100;i++){
//			String a=String.format("%03d",RandomUtils.nextLong(0, 999))+""+System.currentTimeMillis();
//			System.out.println(a);
//		}
		String s="oYFYA0Qlv7tfdhpyvik6KB-dGUNw";
		System.out.println(encodeHex(s.getBytes()).length());
		System.out.println(new String(decodeHex(encodeHex(s.getBytes()))));
		
	}
}

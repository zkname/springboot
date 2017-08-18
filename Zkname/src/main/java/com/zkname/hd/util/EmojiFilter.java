package com.zkname.hd.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

//if(EmojiFilter.containsEmoji(blUser.getNickname())){
//	blUser.setNickname(EmojiFilter.filterEmoji(blUser.getNickname()));
//}

public class EmojiFilter {

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String filterEmoji(String source)  {
		try {
			String name= filterOffUtf8Mb4(source);
			if(name!=null && name.length()>100){
				name=name.substring(0,100);
			}
			return name;
		} catch (UnsupportedEncodingException e) {
			return "-";
		}
	}

	/**
	 * 过滤掉超过3个字节的UTF8字符
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
		byte[] bytes = text.getBytes("utf-8");
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		int i = 0;
		while (i < bytes.length) {
			short b = bytes[i];
			if (b > 0) {
				buffer.put(bytes[i++]);
				continue;
			}
			b += 256; // 去掉符号位
			if (((b >> 5) ^ 0x6) == 0) {
				buffer.put(bytes, i, 2);
				i += 2;
			} else if (((b >> 4) ^ 0xE) == 0) {
				buffer.put(bytes, i, 3);
				i += 3;
			} else if (((b >> 3) ^ 0x1E) == 0) {
				i += 4;
			} else if (((b >> 2) ^ 0x3E) == 0) {
				i += 5;
			} else if (((b >> 1) ^ 0x7E) == 0) {
				i += 6;
			} else {
				buffer.put(bytes[i++]);
			}
		}
		buffer.flip();
		return new String(buffer.array(), "utf-8");
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(filterEmoji(" ާ"));
	}
}
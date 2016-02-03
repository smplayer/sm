package com.sm.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 信息编码工具类
 * @author Frank
 */
public abstract class MessageDigestUtils {

	private static final Logger logger = Logger.getLogger(MessageDigestUtils.class.getName());

	public static final char[] HEX_CHARS = {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	/**
	 * 将字符串用 MD5 进行加密
	 * @param s 明码
	 * @return 加密后的HEX字符串
	 */
	public static String toMD5(String s) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			return toHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "无法获取 MessageDigest 实例", e);
		}
		return null;
	}

	/**
	 * 将字符串用 SHA 进行加密
	 * @param s 明码
	 * @return 加密后的HEX字符串
	 */
	public static String toSHA(String s) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			md.update(s.getBytes());
			return toHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "无法获取 MessageDigest 实例", e);
		}
		return null;
	}

	/**	把<code>byte[]</code>转换成HEX字符串 */
	public static String toHexString(byte[] array) {
		char c[] = new char[array.length * 2];
		for (int i = 0, j = 0; i < array.length; i++) {
			c[j++] = HEX_CHARS[(array[i] >>> 4) & 15];
			c[j++] = HEX_CHARS[array[i] & 15];
		}
		return new String(c);
	}
}

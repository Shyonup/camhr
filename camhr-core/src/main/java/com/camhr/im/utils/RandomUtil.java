package com.camhr.im.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public final class RandomUtil {

	private static final char[] NUMBER_ENGLISH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final char[] NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final char[] ENGLISH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	private static Random random = null;

	private static int DEFAULT_LEN = 4;

	public static int SIXTEEN_BIT_LEN = 16;

	static{
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			random = new SecureRandom();
		}
	}

	private RandomUtil(){}
	
	/**
	 * 随机生成指定位数的密码
	 * @param length
	 * @return
	 */
	public static String random(int length){
		return random(length, NUMBER_ENGLISH);
	}
	
	public static String randomNumber(int length){
		return random(length, NUMBER);
	}

	public static String randomNumber(){
		return random(DEFAULT_LEN, NUMBER);
	}
	
	public static String randomEnglish(int length){
		return random(length, ENGLISH);
	}
	
	private static String random(int length,char [] classify){
		StringBuffer pwdApp = new StringBuffer();
		int char_len = classify.length;
		for (int i = 0; i < length; i ++){
			int num = random.nextInt(char_len - 1);
			char c = classify[num];
			if (random.nextBoolean()){
				pwdApp.append(String.valueOf(c).toUpperCase());
			} else {
				pwdApp.append(String.valueOf(c));
			}
		}
		return pwdApp.toString();
	}
	
	public static String uuid(){
		return UUID.randomUUID().toString();
	}

}

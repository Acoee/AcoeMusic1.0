package com.app.music.common;

import java.util.ArrayList;
import java.util.Locale;
import com.app.music.common.HanziToPinyin.Token;


/**
 * 汉字转拼音工具类
 * @author songqy
 * @version 1.0.0
 * @time 2015-04-27
 *
 */
public class PinyinUtil {

	/**
	 * 将汉字转为全拼和首字母返回（全拼和首字母字符串中间用“-”分隔）
	 * @param source 汉字字符串
	 * @return
	 */
	public static String getFullPinYinAndFirtPinyin(String source) {
		/*if (!Arrays.asList(Collator.getAvailableLocales()).contains(
				Locale.CHINA)) {
			return source;HanziToPinyin.java
		}*/
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(source);
		if (tokens == null || tokens.size() == 0) {
			return source;
		}
		StringBuffer result = new StringBuffer();
		StringBuffer result2 = new StringBuffer();
		for (Token token : tokens) {
			if (token.type == Token.PINYIN) {
				result.append(token.target);
				result2.append(token.target.charAt(0));
			} else {
				result.append(token.source);
			}
		}
		return result.append("-").append(result2.toString()).toString();
	}

	/**
	 * 将汉字转为全拼
	 * @param source 汉字字符串
	 * @return
	 */
	public static String getFullPinYin(String source) {
		/*if (!Arrays.asList(Collator.getAvailableLocales()).contains(Locale.CHINA)) {
			return source;
		}*/
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(source);
		if (tokens == null || tokens.size() == 0) {
			return source;
		}
		StringBuffer result = new StringBuffer();
		for (Token token : tokens) {
			if (token.type == Token.PINYIN) {
				result.append(token.target);
			} else {
				result.append(token.source);
			}
		}
		return result.toString();
	}

	/**
	 * 获取所有汉字的首字母字符串
	 * @param source
	 * @return
	 */
	public static String getFirstPinYin(String source) {
		/*if (!Arrays.asList(Collator.getAvailableLocales()).contains(Locale.CHINA)) {
			return source;
		}*/
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(source);
		if (tokens == null || tokens.size() == 0) {
			return source;
		}
		StringBuffer result = new StringBuffer();
		for (Token token : tokens) {
			if (token.type == Token.PINYIN) {
				result.append(token.target.charAt(0));
			} else {
				result.append("#");
			}
		}
		return result.toString();
	}

	/**
	 * 获取汉字首字母
	 * @param source
	 * @return
	 */
	public static String getSortterletter(String source) {
		if (source != null && source.length() > 1) {
			source = source.substring(0, 1);
		}
		if(SystemUtils.isLetternRight(source)){
			return source.toUpperCase(Locale.CHINA);
		}
		/*if (!Arrays.asList(Collator.getAvailableLocales()).contains(Locale.CHINA)) {
			return "#";
		}*/
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(source);
		if (tokens == null || tokens.size() == 0) {
			return "#";
		}
		String result = "#";
		Token token = tokens.get(0);
		if (token.type == Token.PINYIN) {
			result = String.valueOf(token.target.charAt(0));
		}
		return result.toUpperCase(Locale.CHINA);
	}

}

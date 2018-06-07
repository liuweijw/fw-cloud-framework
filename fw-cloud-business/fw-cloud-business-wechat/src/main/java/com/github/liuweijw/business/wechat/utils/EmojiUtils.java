package com.github.liuweijw.business.wechat.utils;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;

/**
 * 微信昵称带特殊字符和Emoji图标的入库处理
 * 
 * @author liuweijw
 */
public class EmojiUtils {

	public static String toAlias(String emojiString) {
		/*
		 * String str = "  An 😃😀awesome 😃😃string with a few 😃😉emojis!"; String alias = EmojiConverter.getInstance().toAlias(str); System.out.println(str);
		 * System.out.println("EmojiConverterTest.testToAlias()=====>"); System.out.println(alias);
		 */

		if (StringHelper.isBlank(emojiString)) return "";
		return EmojiConverter.getInstance().toAlias(emojiString);
	}

	public static String toHtml(String emojiString) {
		/*
		 * String str = "  An 😀😃awesome 😃😃string with a few 😉😃emojis!"; String result = EmojiConverter.getInstance().toHtml(str); System.out.println(str);
		 * System.out.println("EmojiConverterTest.testToHtml()=====>"); System.out.println(result);
		 */

		if (StringHelper.isBlank(emojiString)) return "";
		return EmojiConverter.getInstance().toHtml(emojiString);
	}

	public static String toUnicode(String emojiString) {
		/*
		 * String str = "   :smiley: :grinning: :wink:"; String result = EmojiConverter.getInstance().toUnicode(str); System.out.println(str);
		 * System.out.println("EmojiConverterTest.testToUnicode()=====>"); System.out.println(result);
		 */

		if (StringHelper.isBlank(emojiString)) return "";
		return EmojiConverter.getInstance().toUnicode(emojiString);
	}

	public static void main(String[] args) {
		String str = "  An 😀😃awesome 😃😃string with a few 😉😃emojis!";
		String result = EmojiConverter.getInstance().toHtml(str);
		System.out.println(str);
		System.out.println("EmojiConverterTest.testToHtml()=====>");
		System.out.println(WebUtils.buildURLEncoder(result));
	}
}

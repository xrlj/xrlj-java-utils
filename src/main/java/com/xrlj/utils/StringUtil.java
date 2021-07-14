package com.xrlj.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * 
 * @author zmt
 *
 */
public final class StringUtil extends org.apache.commons.lang3.StringUtils {

	private final static byte[] randBytes = {'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m','Q','W','E','R',
			'T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M','1','2','3','4','5','6','7','8','9','!','@','#','$','%','^','&','*','(',')','_','+'};

	private StringUtil(){}

	public static void main(String[] args) {
//		System.out.println(javaFieldName2SqlFieldName("UserName"));

//		System.out.println(isInteger("3.9"));

//		System.out.println(isNumeric("3"));

//		System.out.println(getUUID());

//		System.out.println(randomStr(6));

//		List<String> r = splitStrToList(",供应商,,,保理商,项目公司  ,", ",");
		List<String> r = splitStrToList("项目公司", ",");
		System.out.println(r);
	}

	/**
	 * 多个字符用分割字符（比如英文逗号或者中文逗号）隔开，分割转成列表
	 * @param strs	用分隔符隔开的字符串。
	 * @param regex	分割符
	 * @return
	 */
	public static List<String> splitStrToList(String strs, String regex) {
		Objects.requireNonNull(strs, "strs is null");
		Objects.requireNonNull(regex, "regex is null");
		String[] ss = strs.split(regex);
		if (ss == null || ss.length == 0) {
			return null;
		}
		List<String> rList = new ArrayList<>();
		for (int i = 0; i < ss.length; i++) {
			String item = replaceBlank(ss[i]);
			if (!regex.equals(item) && !"".equals(item)) {
				rList.add(item);
			}
		}
		return rList;
	}

	/**
	 * 生成随机的字符串。有小写字母、大写字符、数字、一些特殊字符随机组成的字符串。有相同的几率。
	 * @param step 要返回的字符串的长度。
	 * @return step长度的随机字符串。
	 */
	public static String randomStr(int step) {
		byte[] resultBytes = new byte[step];
		Random random = new Random();
		for (int i = 0; i < step; i++) {
			int index = random.nextInt(randBytes.length);
			resultBytes[i] = randBytes[index];
		}
		String randStr = new String(resultBytes);
		return randStr;
	}

	public static boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 *  判断字符串是否为整数。
 	 * @param str
	 * @return 是整形返回true，否则返回false
	 */
	public static final boolean isInteger(final String str) {
		AssertUtil.nullOrEmpty(str);
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}


	/**
	 * 把java驼峰命名字段改成下划线分割的sql字段。例如：userName -> user_name,GoodsDetails ->
	 * goods_details
	 * 
	 * @param javaFieldName
	 * @return 返回下划线分隔的字符串
	 */
	public static final String javaFieldName2SqlFieldName(final String javaFieldName) {
		if (isEmpty(javaFieldName)) {
			throw new NullPointerException("javaFieldName不能为null");
		}
		StringBuffer sb = new StringBuffer(); // 线程安全，比StringBuilder慢
		for (int i = 0; i < javaFieldName.length(); i++) {
			char c = javaFieldName.charAt(i);
			if (!Character.isLowerCase(c)) {// 大写字母
				if (i != 0) {
					sb.append("_");
				}
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 去掉所有空格
	 * 
	 * @param str
	 * @return
	 */
	public final static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 提取html文件中的文本信息。
	 * 
	 * @param inputString
	 * @return
	 */
	public final static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;

		Pattern p_html1;
		Matcher m_html1;

		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			htmlStr = htmlStr.replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("&amp;", "")
					.replaceAll("&lt;", "").replaceAll("&gt;", "").replaceAll("&nbsp;", "").replaceAll("<br/>", "")
					.replaceAll("&ldquo;", "");
			textStr = htmlStr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return textStr;// 返回文本字符串
	}

	/**
	 * 获取独一无二的字符串系列号。
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 首字母转换成小写。
	 * @param oriStr
	 * @return
	 */
	public static final String firstCharToLower(String oriStr) {
		if (isEmpty(oriStr)) {
			throw new NullPointerException("参数不能为空");
		}
		String newStr = oriStr;
		StringBuffer sb = new StringBuffer(); // 线程安全，比StringBuilder慢
		for (int i = 0; i < oriStr.length(); i++) {
			char c = oriStr.charAt(i);
			if (i == 0 && Character.isLowerCase(c)) {
				break;
			}
			if (i == 0) {
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
			newStr = sb.toString();
		}

		return newStr;
	}

	// public static void main(String[] args) {
	// StringBuffer htmlStr = new StringBuffer();
	// htmlStr.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0
	// Transitional//EN'
	// 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>")
	// .append("<html xmlns='http://www.w3.org/1999/xhtml'
	// xml:lang='en'><head><title>aaa</title><mce:script
	// type='text/javascript'></mce:script>")
	// .append("<link href='static_files/help.css'
	// mce_href='static_files/help.css' rel='stylesheet' type='text/css'
	// media='all' />")
	// .append("</head><body><ul><li>XXXX</li></ul></body></html>")
	// .append("<span style='line-height: 19px; font-family: 宋体; color:
	// rgb(85,85,85)'>李志峰将货物来回老家，扣货要求货主赔偿，强迫货主到河南商丘处理，后货主按司机要求打钱，司机安排另一台车将货倒走，送到目的地。无论什么原因，扣货要挟的司机一律被列为驾驶员黑名单。</span></p>");
	//
	// System.out.println(Html2Text(htmlStr.toString()));
	// }

}

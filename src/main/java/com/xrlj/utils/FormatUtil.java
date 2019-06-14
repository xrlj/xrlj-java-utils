package com.xrlj.utils;
/**
 * 格式化输入工具类
 * 
 * @author 郑坚焱
 * @date 2017-05-14
 * @Modified 2017-04-28
 * 
 */
public final class FormatUtil {

	/**
	 * 打印输入到控制台
	 * 
	 * @param jsonString
	 * @author 郑坚焱
	 * @Date 2017-05-14 下午1:17:22
	 */
	public static void printJSON(String jsonString) {
		System.out.println(formatJSON(jsonString));
	}

	/**
	 * 格式化
	 * 
	 * @param jsonString
	 * @return
	 * @author 郑坚焱
	 * @Date 2017-05-14 下午1:17:35
	 * @Modified 2017-04-28 下午8:55:35
	 */
	public static String formatJSON(String jsonString) {
		if (null == jsonString || "".equals(jsonString))
			return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		boolean isInQuotationMarks = false;
		for (int i = 0; i < jsonString.length(); i++) {
			last = current;
			current = jsonString.charAt(i);
			switch (current) {
			case '"':
                                if (last != '\\'){
				    isInQuotationMarks = !isInQuotationMarks;
                                }
				sb.append(current);
				break;
			case '{':
			case '[':
				sb.append(current);
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
				}
				break;
			case '}':
			case ']':
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
				}
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\' && !isInQuotationMarks) {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}

		return sb.toString();
	}

	/**
	 * 添加space
	 * 
	 * @param sb
	 * @param indent
	 * @author 郑坚焱
	 * @Date 2017-05-14 上午10:38:04
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}
	
    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式  
     * @param n 数字
     * @return
     */
    public static String digitUppercase(double n) {  
        String fraction[] = {"角", "分"};  
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};  
        String unit[][] = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};  
      
        String head = n < 0 ? "负" : "";  
        n = Math.abs(n);  
      
        String s = "";  
        for (int i = 0; i < fraction.length; i++) {  
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");  
        }  
        if (s.length() < 1) {  
            s = "整";  
        }  
        int integerPart = (int) Math.floor(n);  
      
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {  
            String p = "";  
            for (int j = 0; j < unit[1].length && n > 0; j++) {  
                p = digit[integerPart % 10] + unit[1][j] + p;  
                integerPart = integerPart / 10;  
            }  
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;  
        }  
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");  
    }  
}
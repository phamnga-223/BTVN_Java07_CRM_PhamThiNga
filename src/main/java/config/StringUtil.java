package config;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	public static String convert(String s) throws UnsupportedEncodingException {
		String result;
		byte[] nameBytes = s.getBytes("ISO-8859-1"); 
		result = new String (nameBytes);
		
		return result;
	}
}

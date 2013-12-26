package cn.evan.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

	/**
	 * 把输入流转换成数组
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	/**
	 * 中文转码
	 * 
	 * @param str
	 * @return
	 */
	public static String uniCode(String str) {
		String name = "";
		// 字符转码
		try {
			name = URLEncoder.encode(str, "UTF-8");
			// name = new String(str.getBytes(), "UTF-8"); "GBK"
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
	}

}
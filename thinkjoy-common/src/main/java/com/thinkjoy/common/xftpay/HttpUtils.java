package com.thinkjoy.common.xftpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 * 
 * @author HeHongxin
 * @date 2014-7-29
 * 
 */

public class HttpUtils {

	/** UTF-8编码名称(UTF-8) */
	public static final String UTF8_NAME = "UTF-8";
	/** UTF-8编码 */
	public static final Charset UTF8 = character(UTF8_NAME);
	
	private HttpUtils() {
	}

	/**
	 * 格式化url
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 符合http规则的url
	 */
	public static String fmtUrl(String url) {
		url = trim(url);
		if (null != url) {
			return url.replaceAll(" ", "%20");
		}
		return null;
	}
	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charset
	 *            编码charset(缺省UTF-8)
	 * @return 所代表远程资源的响应结果
	 */
	public static String post(String url, String param, Charset charset) {
		url = fmtUrl(url);
		if (null == url) {
			return null;
		}
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			if (null == charset) {
				charset = UTF8;
			}
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			param = trim(param);
			if (null != param) {
				// 获取URLConnection对象对应的输出流
				OutputStreamWriter ow = new OutputStreamWriter(conn.getOutputStream(), charset);
				out = new PrintWriter(ow);
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
				try {
					out.close();
				} catch (Exception e) {
				}
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 *
	 * @param url
	 * @param param
	 * @param charset
	 * @param contentType 请求数据格式
     * @return
     */
	public static String post(String url, String param, Charset charset, String contentType) {
		url = fmtUrl(url);
		if (null == url) {
			return null;
		}
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			if (null == charset) {
				charset = UTF8;
			}
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", contentType);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			param = trim(param);
			if (null != param) {
				// 获取URLConnection对象对应的输出流
				OutputStreamWriter ow = new OutputStreamWriter(conn.getOutputStream(), charset);
				out = new PrintWriter(ow);
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
				try {
					out.close();
				} catch (Exception e) {
				}
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	/**
	 * 读取http请求数据，校付通专用请勿删除
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param charset
	 *            编码名称(缺省UTF-8)
	 * @return 请求数据
	 */
	public static String postData(HttpServletRequest request, String charset) {
		StringBuilder source = null;
		InputStream is = null;
		try {
			if (null == charset) {
				charset = UTF8_NAME;
			}
			// 读取http请求内容
			source = new StringBuilder();
			is = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
			String buffer = null;
			while ((buffer = br.readLine()) != null) {
				source.append(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != is) {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return null != source ? source.toString() : null;
	}
	/**
	 * 页面直接输出明文内容
	 * 
	 * @param response
	 *            HttpServletResponse对象
	 * @param msg
	 *            输出内容(text/html; charset=UTF-8)
	 */
	public static void print(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(msg);
	}
	
	
	/**
	 * trim字符串
	 * 
	 * @param s
	 *            字符串
	 * @return 非空返回trim后的字符串，否则返回null
	 */
	public static String trim(String s) {
		if (null != s) {
			s = s.trim();
			if (!s.isEmpty()) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * 获取编码
	 * 
	 * @param charsetName
	 *            编码名称
	 * @return 编码/NULL
	 */
	public static Charset character(String charsetName) {
		if (null != charsetName) {
			if (Charset.isSupported(charsetName)) {
				try {
					return Charset.forName(charsetName);
				} catch (UnsupportedCharsetException x) {
					throw new Error(x);
				}
			}
		}
		return null;
	}

}

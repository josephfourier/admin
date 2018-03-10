package com.thinkjoy.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 文件操作工具类，对上传文件进行操作-获取文件名、文件类型、文件路径、保存文件
 * @Date 2014-3-12-上午9:35:40
 * @Author: qizai
 * @Version: V1.0.0
 */
public class FileUtil {
	/**
	 * 删除文件，根据请求的上下文获取文件的实际地址删除该文件
	 * 
	 * @param request
	 * @param filePath
	 *            文件的相对地址
	 */
	public static void deleteFiles(HttpServletRequest request,
			String... filePath) {
		if (null == filePath || filePath.length == 0) {
			return;
		}
		ServletContext sc = request.getSession().getServletContext();
		for (String path : filePath) {
			if (StringUtils.isNotBlank(path)) {
				String real_path = sc.getRealPath(path);
				deleteFiles(real_path);
			}
		}
		return;
	}

	/**
	 * 根据实际路径删除文件
	 * 
	 * @param filePath
	 *            实际路径
	 */
	public static void deleteFiles(String... filePath) {
		if (null == filePath || filePath.length == 0) {
			return;
		}
		try {
			for (String path : filePath) {
				if (StringUtils.isNotBlank(path)) {
					FileUtils.deleteQuietly(new File(path));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * 根据相对路径获取文件的实际路径
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request, String path) {
		if (StringUtils.isBlank(path)) {
			return EMPTY_STRING;
		}
		ServletContext sc = request.getSession().getServletContext();
		return sc.getRealPath(path);
	}

	/**
	 * 把上传的文件保存到本地
	 * 
	 * @param source
	 *            上传文件资源
	 * @param filePath
	 *            文件的实际完整地址
	 * @return
	 */
	public static boolean writeToDest(MultipartFile source, String filePath) {
		boolean operateSuccess = true;
		if (StringUtils.isBlank(filePath)) {
			return false;
		}
		try {
			// 文件目录地址
			File directoryFile = new File(filePath.substring(0,
					filePath.lastIndexOf(File.separator)));
			// 先建立文件目录
			if (!directoryFile.exists()) {
				directoryFile.mkdirs();
			}
			// 文件不存在则创建文件
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			source.transferTo(file);
		} catch (IllegalStateException e) {
			operateSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			operateSuccess = false;
			e.printStackTrace();
		}
		return operateSuccess;
	}

	public static boolean writeToDest(InputStream source, String filePath) {
		boolean operateSuccess = true;
		if (StringUtils.isBlank(filePath)||null==source) {
			return false;
		}
		try {
			// 文件目录地址
			File directoryFile = new File(filePath.substring(0,
					filePath.lastIndexOf(File.separator)));
			// 先建立文件目录
			if (!directoryFile.exists()) {
				directoryFile.mkdirs();
			}
			// 文件不存在则创建文件
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileUtils.copyInputStreamToFile(source, file);
		} catch (IllegalStateException e) {
			operateSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			operateSuccess = false;
			e.printStackTrace();
		}
		return operateSuccess;
	}

	/**
	 * 获取上传的文件，同名只能取到一个
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, MultipartFile> getUploadFile(
			HttpServletRequest request) {
		// 设置上下方文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form是否有enctype="multipart/form-data"
		if (!multipartResolver.isMultipart(request)) {
			return Collections.emptyMap();
		}
		Map<String, MultipartFile> fileMap = Maps.newLinkedHashMap();
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			// 由CommonsMultipartFile继承而来,拥有上面的方法.
			String fileReqName = iter.next();
			MultipartFile file = multiRequest.getFile(fileReqName);
			if (file != null && file.getSize() > 0) {
				fileMap.put(fileReqName, file);
			}
		}
		return fileMap;
	}

	/**
	 * 只获取表单中的一个文件
	 * 
	 * @param request
	 * @return
	 */
	public static MultipartFile getUploadFileOne(HttpServletRequest request) {
		// 设置上下方文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form是否有enctype="multipart/form-data"
		if (!multipartResolver.isMultipart(request)) {
			return null;
		}
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			// 由CommonsMultipartFile继承而来,拥有上面的方法.
			MultipartFile file = multiRequest.getFile(iter.next());
			if (null == file || file.isEmpty()) {
				return null;
			}
			return file;
		}
		return null;
	}

	/**
	 * 获取文件类型,无.
	 * 
	 * @param filename
	 * @return
	 */
	public static final String getExtension(String filename) {
		if (StringUtils.isBlank(filename)) {
			return EMPTY_STRING;
		}
		return FilenameUtils.getExtension(filename);
	}

	/**
	 * 获取文件类型后缀.例如：
	 * 
	 * <pre>
	 * data.xml  =  .xml
	 * data.JPG  =  .JPG
	 * </pre>
	 * 
	 * @param file
	 * @return
	 */
	public static final String getExtension(MultipartFile file) {
		String filename = file.getOriginalFilename();
		String extension = FileUtil.getExtension(filename);

		return FILE_TYPE + extension;
	}

	public static final String EMPTY_STRING = "";
	public static final String FILE_TYPE = ".";

	/**
	 * 获取目录路径，例如:use/local -> use/local/
	 * 
	 * @param path
	 * @return
	 */
	public static String getDirPath(String path) {
		if (!path.endsWith(File.separator)) {
			return path + File.separator;
		}
		return path;
	}
}

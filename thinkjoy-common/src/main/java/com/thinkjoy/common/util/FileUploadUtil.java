package com.thinkjoy.common.util;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Decoder;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by xufei
 */
public class FileUploadUtil {
    private final static RestTemplate template = new RestTemplate();

    /**
     * 文件名默认为file，返回fileurl
     * @param request
     * @return
     */
    public static String saveFileToDist(HttpServletRequest request) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form是否有enctype="multipart/form-data"∂
        if (!multipartResolver.isMultipart(request)) {
            return null;
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;


        //这里都是单文件上传,所以循环一次
        MultipartFile multipartFile = multiRequest.getFile("file");
//        if (multipartFile.getSize() > 1024 * 1024 * 10L) {
//            return null;
//        }
        //分布式部署
        //这里创建一个临时文件
        ServletContext servletContext = request.getSession().getServletContext();
        String upload=servletContext.getRealPath("/upload");
        if(null==upload){
            upload=servletContext.getRealPath("/")+"/upload";
        }
        String filePath = upload + File.separator + multipartFile.getOriginalFilename();
        // 创建一个临时文件,这里的临时文件主要是用来将CommonsMultipartFile转成File
        File upLoadfile = new File(filePath);
        if (!upLoadfile.exists()) {
            upLoadfile.mkdirs();
        }

        try {
            multipartFile.transferTo(upLoadfile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        FileSystemResource fileSystemResource = new FileSystemResource(upLoadfile);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", fileSystemResource);
        param.add("fileName", multipartFile.getOriginalFilename());

        String url = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.file.server.url");

        String fileurl = template.postForObject(url, param, String.class);
        upLoadfile.delete();
        if (fileurl == null) {
            return null;
        }
        return fileurl;
    }

    /**
     * 根据文件名 ， 返回fileurl
     * @param request
     * @param file
     * @return
     */

    public static String saveFileToDist(HttpServletRequest request, String file) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form是否有enctype="multipart/form-data"
        if (!multipartResolver.isMultipart(request)) {
            return null;
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;


        //这里都是单文件上传,所以循环一次
        MultipartFile multipartFile = multiRequest.getFile(file);
        if (multipartFile == null) {
            return null;
        }

        //分布式部署

        //这里创建一个临时文件
        ServletContext servletContext = request.getSession().getServletContext();
        String upload=servletContext.getRealPath("/upload");
        if(null==upload){
            upload=servletContext.getRealPath("/")+"/upload";
        }
        String filePath = upload + File.separator + multipartFile.getOriginalFilename();

        // 创建一个临时文件,这里的临时文件主要是用来将CommonsMultipartFile转成File
        File upLoadfile = new File(filePath);
        if (!upLoadfile.exists()) {
            upLoadfile.mkdirs();
        }

        try {
            multipartFile.transferTo(upLoadfile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        FileSystemResource fileSystemResource = new FileSystemResource(upLoadfile);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", fileSystemResource);
        param.add("fileName", multipartFile.getOriginalFilename());

        String url = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.file.server.url");
        String fileurl = template.postForObject(url, param, String.class);
        upLoadfile.delete();
        if (fileurl == null) {
            return null;
        }
        return fileurl;
    }

    /**
     * 根据文件路径，文件名
     * @param filePath
     * @param fileName
     * @return
     */
    public static String saveFileRemote(String filePath, String fileName) {
        String fileurl = "";
        File upLoadfile = new File(filePath);

        if (upLoadfile.exists()) {

            FileSystemResource fileSystemResource = new FileSystemResource(upLoadfile);

            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", fileSystemResource);
            param.add("fileName", fileName);

            String url = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.file.server.url");

            fileurl = template.postForObject(url, param, String.class);
            if (fileurl == null) {
                return null;
            }
        }
        return fileurl;
    }


    /**
     * 参数为file，文件名称
     *
     * @param file
     * @return
     */
    public static String saveFileToDist(File file, String fileName) {


        FileSystemResource fileSystemResource = new FileSystemResource(file);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", fileSystemResource);
        param.add("fileName", fileName);

        String url = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.file.server.url");
        String fileurl = template.postForObject(url, param, String.class);
        if (fileurl == null) {
            return null;
        }
        return fileurl;
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        try {
            // Base64解码
            byte[] bytes = decodeBase64ToByteArr(imgStr);
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static byte[] decodeBase64ToByteArr(String imgStr) {// 对字节数组字符串进行Base64解码
        if (imgStr == null) // 图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] bytes = null;
        try {
            bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


    public static String uploadBase64Image(HttpServletRequest request, String imgStr, String fileName) throws Exception {

        if (StringUtils.isEmpty(imgStr)) {
            return "";
        }
        ServletContext servletContext = request.getSession().getServletContext();
        String upload=servletContext.getRealPath("/upload");
        if(null==upload){
            upload=servletContext.getRealPath("/")+"/upload";
        }
        String s_path = upload + File.separator + fileName;
        if (!GenerateImage(imgStr, s_path)) {
            return "";
        }
        File file = new File(s_path);


        FileSystemResource fileSystemResource = new FileSystemResource(file);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", fileSystemResource);
        param.add("fileName", fileName);

        String url = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.file.server.url");
        String fileurl = template.postForObject(url, param, String.class);

        file.delete();
        if (fileurl == null) {
            return null;
        }
        return fileurl;
    }

    public static void main(String[] args) {

        String url = "http://localhost:8088/file-server/file/upload";
        String request = "";
        ResponseEntity response = template.postForEntity(url, request, String.class);

        System.out.println(response.getBody().toString());

    }
}

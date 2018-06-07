package cmei.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * WebFileDownloader
 *
 * @author meicanhua
 * @create 2017-10-24 上午10:50
 **/
public class WebFileDownloader {
    private static Logger logger = LoggerFactory.getLogger(WebFileDownloader.class);

    /**
     * 从输入流中下载文件，适用大文件
     * @param response
     * @param fileName
     * @param inputStream
     * @throws IOException
     */
    public void downloadFile(HttpServletResponse response, String fileName, InputStream inputStream) throws IOException {
        Long startTime = System.currentTimeMillis();
        String urlName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + urlName);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        Long endTime = System.currentTimeMillis();
        logger.info("downloadFile", "download time:" + (endTime - startTime));
    }

    /**
     * 从数组中下载文件， 适用于小文件
     * @param response
     * @param fileName
     * @param bytes
     * @throws IOException
     */
    public void downloadFile(HttpServletResponse response, String fileName, byte[] bytes) throws IOException {
        Long startTime = System.currentTimeMillis();
        String urlName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + urlName);
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setContentLength(bytes.length);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.writeChunked(bytes, outputStream);
        outputStream.flush();
        outputStream.close();
        Long endTime = System.currentTimeMillis();
        logger.info("downloadFile", "download " + fileName +" time:" + (endTime - startTime));
    }
}
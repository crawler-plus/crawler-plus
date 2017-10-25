package com.crawler.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * FTP工具类
 */
public class FtpUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    /**
     * 上传文件到FTP服务器
     */
    public static void fileUploadByFtp(String host, String username, String password, String workingDirectory, InputStream is, String fileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(workingDirectory);
            ftpClient.storeFile(fileName, is);
            ftpClient.logout();
        }catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn(e.getMessage());
            }
        }finally {
            IOUtils.closeQuietly(is);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                if(logger.isWarnEnabled()) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }
}

package com.crawler.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * FTP工具类
 */
public class FtpUtils {

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
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(is);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

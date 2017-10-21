package com.crawler.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

public class FtpUtils {

    /**
     *
     */
    public static void fileUploadByFtp(String host, String username, String password, String workingDirectory, InputStream is, String fileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host);
            ftpClient.login(username, password);
            ftpClient.changeWorkingDirectory(workingDirectory);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileName, is);
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

package com.crawler.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.URL;

public class DownloadFileUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static ResponseEntity<byte[]> download(String fileName, File file)
			throws IOException {
		fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}

	/**
	 * 从网上爬取pdf文件
	 * @throws Exception
	 */
	public static void crawPdf(String sourceUrl, String fileName) {
		URL url;
		InputStream in = null;
		OutputStream out = null;
		try {
			url = new URL(sourceUrl);
			in = url.openStream();
			out = new BufferedOutputStream(new FileOutputStream(fileName));
			for (int b; (b = in.read()) != -1;) {
				out.write(b);
			}
		} catch (Exception e) {
			LoggerUtils.printExceptionLogger(logger, e);
		}finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
}

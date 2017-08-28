package com.zkname.frame.util.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

/**
 * 文件下载
 * @author ZhangKai
 *
 */
public class DownloadUtil {

	protected final static byte commonCsvHead[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };

	protected void dowCvs(HttpServletRequest request, HttpServletResponse response, String filename, StringBuffer sb) {
		try (OutputStream ouputStream = response.getOutputStream()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download;charset=UTF-8");
			// 文件名
			response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");
			ouputStream.write(Bytes.concat(commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void dow(HttpServletRequest request, HttpServletResponse response, String filename, File file) {
		try (OutputStream out = response.getOutputStream(); FileInputStream inputStream = new FileInputStream(file)) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download;charset=UTF-8");
			// 文件名
			response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");
			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				// 4.写到输出流(out)中
				out.write(buffer, 0, b);
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

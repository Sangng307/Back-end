package com.poly.demo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


@Component

public class QRCodeGenerator {
	

	
	public String generateORcode(String data) throws WriterException, IOException {
		String timestamp = String.valueOf(System.currentTimeMillis());
		String fileName="qrcode_"+timestamp+".jpg";

		String absolutePath = "D:\\Blockchain\\workspace\\NestJs" + fileName;
		//Tạo qr code
		BitMatrix matrix = new MultiFormatWriter().encode(data,BarcodeFormat.QR_CODE, 300, 300);
		//Lưu Qr code vào resource
		
		
		MatrixToImageWriter.writeToPath(matrix,"jpg",Paths.get(absolutePath));
		System.out.println("Tạo mã qr thành công");
		System.out.println(absolutePath);
		return absolutePath;
	}

}
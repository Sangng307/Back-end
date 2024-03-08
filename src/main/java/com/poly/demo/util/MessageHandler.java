package com.poly.demo.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class MessageHandler {
	public static List<String> createMessageList(){
		List<String> mess = new ArrayList<String>();
		mess.add(0,"Xác thực tài khoản tại hệ thống CFS");
		mess.add(1,"Bạn vui lòng nhấp vào đường link để xác thực tài khoản http://localhost:8080/api/active/user/");
		mess.add(2,"Hệ Thống Tài Khoản CFS");
		mess.add(3,"Mật khẩu của bạn đã được reset thành : ");
		mess.add(4,"\nHãy vào ứng dụng và đổi lại thành mật khẩu của mình");
		mess.add(5,"Đơn Hàng CFS");
		mess.add(6,"Bạn đã đặt hàng thành công - Bạn vui lòng kiểm tra trạng thái đơn hàng trong ứng dụng");
		mess.add(7,"Tài khoản của bạn đã được khôi phục thành công");
		mess.add(8,"http://localhost:8080/api/active/user/");
		mess.add(9,"<img src='C:/Users/Admin/Desktop/qrcode_1701380289240cc' /></body></html>");	
		return mess;
	}

}

//package com.poly.demo.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.poly.demo.Dao.UsersDao;
//import com.poly.demo.Entity.Users;
//
//import jakarta.mail.PasswordAuthentication;
//
//@Service
//
//public class ChangePasswordService {
//	@Autowired 
//	UsersDao usersDao;
//	
//	public boolean changePassword(String username,String currentPass,String newPass,String confirmPass) {
//		Users user = usersDao.findById(username).get();
//		if(user.getStatus()==true) {
//			if(!user.getPassword().equalsIgnoreCase(currentPass)) {
//				return false;
//			}
//		}else {
//			return false;
//		}
//		if(!newPass.equals(confirmPass)){
//			System.out.println("Hello");
//			return false;
//		}
//		
//		user.setPassword(newPass);
//		usersDao.save(user);
//		return true;
//	}
//	
//}

package com.poly.demo.admincontroller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.poly.demo.Entity.Order;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.zxing.WriterException;
import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Authority;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.Users;
import com.poly.demo.Service.EmailSenderService;
import com.poly.demo.util.MessageHandler;
import com.poly.demo.util.QRCodeGenerator;


@RestController
@RequestMapping("/admin")
public class AdminUsersController {
    @Autowired
    UsersDao userdao;
    @Autowired
    MessageHandler messageHandler;
    @Autowired
    QRCodeGenerator codeGenerator;
    @Autowired
    EmailSenderService emailSenderService;

    @PutMapping("/userStatus/{userId}") // Change orderId to userId
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long userId) {
        try {
            Users user = userdao.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Toggle the user status
            boolean currentStatus = user.isStatus(); // Get the current status
            user.setStatus(!currentStatus); // Toggle the status

            // Save the updated user
            userdao.save(user);

            return ResponseEntity.ok("User status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user status");
        }
    }

    @GetMapping(value = {"/user","/users"})
    public ResponseEntity<List<Users>> getListUser(){
        List<Users> users = userdao.findAll();
        return ResponseEntity.ok(users);
    }



    @GetMapping(value = {"/user/{id}","/users/{id}"})
    public ResponseEntity<Users> getuser(@PathVariable("id") Long id){
        if (!userdao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userdao.findById(id).get());
    }
//	@PostMapping("/create/user")
//	public Users createBook(@RequestBody Users users) {
//
//		System.out.println(users);
//	   return userdao.save(users);
//	}

    //Link xác thực qua Mail
//	@GetMapping("/active/user/{userId}")
//	public String active(@PathVariable("userId")Long userId) {
//
//		Users users= userdao.findById(userId).get();
//		users.setStatus(true);
//		userdao.save(users);
//		return "Actived your account";
//	}
    @GetMapping("/user/{username}")
    public ResponseEntity<Users> getOne(@PathVariable("username") Long userId){
        if (!userdao.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userdao.findById(userId).get());
    }
    @PutMapping("update/user/{username}")
    public Users update(@PathVariable("username")Long username,@RequestBody Users users){
        Users user = userdao.findById(username).get();
        System.out.println(users);
        userdao.save(users);
        return users;
    }
//	@DeleteMapping("/delete/user/{username}")
//	public ResponseEntity<Void> delete(@PathVariable("username") String usersname) {
//		userdao.deleteById(usersname);
//	    return ResponseEntity.noContent().build();
//	}

    @PutMapping("update/user/role/{userId}")
    public String updateRole(@RequestBody JsonNode userInfo,
                             @PathVariable("userId") Long userId
    ) {
//		Users user = userdao.findById(userId).get();
//		List<Authority> listIdAuth = user.getAuthorities();
//		List<String> listRoleId = new ArrayList<>();
//		for(Authority list : listIdAuth) {
//			System.out.println(list.getRole().getId());
//
//		}
//
//		for(int i = 0 ; i < userInfo.size();i++) {
//			listRoleId.add(userInfo.get("id").asText());
//		}
////		System.out.println(userInfo);
//		System.out.println(listRoleId);
////		String[] arrRole = userInfo.findValue("roleId").asText()
////		System.out.println(userInfo.findValue("roleId").asText());
        return"update";
    }

//	@PostMapping("/user/create")
//	public String createBook(@RequestBody Users user) throws WriterException, IOException {
//		try {
//		// get list message from messahandler
//		List<String> mess = messageHandler.createMessageList();
//		Users users = userdao.findByEmail(user.getEmail());
//		String data = mess.get(8)+users.getId();
//		System.out.println(users.getId());
//		codeGenerator.generateORcode(data);
//		users.setStatus(false);
//		emailSenderService.sendMailWithORcode(users.getEmail(), mess.get(0), mess.get(1)+users.getId()+mess.get(9),users);
//	   return "sended QR";
//	   }
//		catch(IOException err) {
//			return "not send";
//		}
//	}

}
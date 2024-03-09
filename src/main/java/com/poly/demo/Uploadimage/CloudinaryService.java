package com.poly.demo.Uploadimage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlxwm5pax",
                "api_key", "468391569652956",
                "api_secret", "ousq7cv8YLXl6_AjvAA4JYt7gxU"));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Map<String, Object> params = ObjectUtils.asMap(
                "folder", "product",
                "allowed_formats", new String[]{"jpg", "png", "jpeg"}
        );

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        System.out.println(uploadResult);
        return (String) uploadResult.get("secure_url");
    }
}


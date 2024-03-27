package org.itheima.reggie.controller;

import org.itheima.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /*
     *
     * 文件上传*/
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {

        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        String s = UUID.randomUUID().toString() + substring;
        File file1 = new File(basePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return R.success(s);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        File file = new File(basePath + name);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
            }
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

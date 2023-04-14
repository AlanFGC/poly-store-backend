package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/upload/glb/{productId}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product uploadGlB(@PathVariable int productId, MultipartFile file) throws IOException {
        Product product = productService.findProductById(productId);
        if (product == null) {
            return null;
        }
        File convertFile = new File("/var/tmp/" + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        return product;
    }

    // https://www.tutorialspoint.com/spring_boot/spring_boot_file_handling.htm

    
}

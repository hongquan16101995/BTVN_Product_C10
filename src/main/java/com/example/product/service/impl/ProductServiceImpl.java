package com.example.product.service.impl;

import com.example.product.model.Product;
import com.example.product.repository.IProductRepository;
import com.example.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;

    @Value("${upload}")
    private String uploadPath;

    @Override
    public Page<Product> findAll(String name, Pageable pageable) {
        if (name != null) {
            return iProductRepository.findName("%" + name + "%", pageable);
        }
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Product findOne(Long id) {
        return iProductRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Product product) {
        MultipartFile image = product.getImage();
        if (image.isEmpty() && product.getId() != null) {
            Product p = findOne(product.getId());
            product.setImagePath(p.getImagePath());
        } else if (image.isEmpty() && product.getId() == null) {
            product.setImagePath("default.jpg");
        } else {
            String fileName = image.getOriginalFilename();
            try {
                FileCopyUtils.copy(image.getBytes(), new File(uploadPath + fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            product.setImagePath(fileName);
        }
        iProductRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        iProductRepository.deleteById(id);
    }
}

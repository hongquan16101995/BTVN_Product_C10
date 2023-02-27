package com.example.product.controller;

import com.example.product.model.Product;
import com.example.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @GetMapping("/home")
    public ModelAndView findAll(@PageableDefault(size = 3) Pageable pageable,
                                @RequestParam("search") String name) {
        ModelAndView modelAndView = new ModelAndView("views/list");
        modelAndView.addObject("products", iProductService.findAll(name, pageable));
        modelAndView.addObject("search", name);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView findOne(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("views/detail");
        modelAndView.addObject("product", iProductService.findOne(id));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("views/form");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("views/form");
        modelAndView.addObject("product", iProductService.findOne(id));
        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        iProductService.save(product);
        return "redirect:/home?search=";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        iProductService.delete(id);
        return "redirect:/home?search=";
    }
}

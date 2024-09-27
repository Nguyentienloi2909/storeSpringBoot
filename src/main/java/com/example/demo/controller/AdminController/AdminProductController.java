package com.example.demo.controller.AdminController;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.validation.AddProduct;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")

public class AdminProductController {
    public static final Logger logger = LoggerFactory.getLogger(AdminProductController.class);
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public AdminProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }
    // test postman
    @GetMapping(value="getALlSP", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<AddProduct>> getAllProducts() {
        List<Product> products = this.productService.getAllProduct();
        List<AddProduct> addProducts = products.stream()
                .map(product -> new AddProduct(
                        product.getIdProduct(),
                        product.getProductName(),
                        product.getUnitPrice(),
                        product.getStockQuantity(),
                        product.getImageUrl(),
                        product.getDescription(),
                        product.isProductStatus(),
                        product.getCategory().getIdCategory()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(addProducts);
    }

    @GetMapping(value="getProduct/{id}")
    @ResponseBody
    public ResponseEntity<AddProduct> getProductById(@PathVariable int id){
        Product product = this.productService.findById(id);
        AddProduct addProduct = new AddProduct(product.getIdProduct(), product.getProductName(), product.getUnitPrice(),
                product.getStockQuantity(), product.getImageUrl() ,product.getDescription(), product.isProductStatus(),product.getCategory().getIdCategory());
        return ResponseEntity.ok(addProduct);
    }



    // PRODUCT
    @GetMapping("quanLyProduct")
    public String quanLyProduct(Model model){
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("products", products);
        return "/admin/products/indexProduct";
    }



    @GetMapping("add_product")
    public String pageAddProduct(Model model){
        List<Category> categories = this.categoryService.getALl();
        AddProduct addProduct = new AddProduct();
        addProduct.setProductStatus(true);
        model.addAttribute("addProduct", addProduct);
        model.addAttribute("categories", categories);
        return "/admin/products/addProduct";
    }

    @PostMapping("add_product")
    public String addProduct(@Valid @ModelAttribute("addProduct") AddProduct addProduct,
                             BindingResult result,
                             @RequestParam("image") MultipartFile image,
                             Model model
    ){
        String fileName = UUID.randomUUID().toString()+"."+image.getOriginalFilename();
        if(result.hasErrors() || addProduct.getIdCategory() == 0 || fileName.isEmpty()){
            List<Category> categories = this.categoryService.getALl();
            model.addAttribute("addProduct", addProduct);
            model.addAttribute("categories", categories);
            model.addAttribute("eImage", "Vui lòng upload ảnh");
            model.addAttribute("eIdCategory", "Vui lòng chọn loại sản phầm");
            return "/admin/products/addProduct";

        }


        // nếu không lỗi
        Product product = new Product();
        product.setProductName(addProduct.getProductName());
        product.setUnitPrice(addProduct.getUnitPrice());
        product.setStockQuantity(addProduct.getStockQuantity());
        product.setDescription(addProduct.getDescription());
        product.setCategory(this.categoryService.findById(addProduct.getIdCategory()));
        product.setProductStatus(addProduct.isProductStatus());

        // xử lý ảnh upload
        product.setImageUrl(fileName);
        try{
            Files.copy(image.getInputStream(), Path.of("src/main/resources/static/fe/images/test/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(this.productService.addProduct(product) != null){
            return "redirect:/admin/quanLyProduct";
        }else{
            model.addAttribute("addProduct", addProduct);
            return "/admin/products/addProduct";
        }

    }

    @GetMapping("edit_product/{idProduct}")
    public String pageEditProduct(@PathVariable("idProduct") int id, Model model){
        Product product = this.productService.findById(id);
        if(product != null ){
            AddProduct addProduct = new AddProduct(product.getIdProduct(), product.getProductName(), product.getUnitPrice(),
                    product.getStockQuantity(), product.getImageUrl() ,product.getDescription(), product.isProductStatus(),product.getCategory().getIdCategory());
            List<Category> categories = this.categoryService.getALl();
            model.addAttribute("addProduct", addProduct);
            logger.info("imageUrl ban đầu: {}", product.getImageUrl());
            model.addAttribute("categories", categories);
            return "/admin/products/editProduct";
        }else{
            return "redirect:/admin/quanLyProduct";
        }

    }

    @PostMapping("edit_product")
    public String updateProduct(@Valid @ModelAttribute("addProduct") AddProduct addProduct,
                                BindingResult result,
                                @RequestParam( name = "image", required = false) MultipartFile image,
                                @RequestParam(name = "formerImage", required = false)String formerImage,
                                Model model
    ){
        List<Category> categories = this.categoryService.getALl();
        if(result.hasErrors() || addProduct.getIdCategory() == 0) {
            if (addProduct.getIdCategory() == 0){
                model.addAttribute("eIdCategory", "Vui lòng chọn loại sản phầm");
            }
            model.addAttribute("addProduct", addProduct);
            model.addAttribute("categories", categories);
            return "/admin/products/editProduct";
        }

        // nếu không lỗi
        Product product = new Product();
        product.setIdProduct(addProduct.getIdProduct());
        product.setProductName(addProduct.getProductName());
        product.setUnitPrice(addProduct.getUnitPrice());
        product.setStockQuantity(addProduct.getStockQuantity());
        product.setDescription(addProduct.getDescription());
        product.setCategory(this.categoryService.findById(addProduct.getIdCategory()));
        product.setProductStatus(addProduct.isProductStatus());

        // xử lý ảnh upload

        if(image == null){
            product.setImageUrl(formerImage);
            logger.info("formerImage: {}", formerImage);
        }else {
            String fileName = UUID.randomUUID().toString() + "." + image.getOriginalFilename();
            try {
                if(formerImage != null){
                    Path filePath = Paths.get("src/main/resources/static/fe/images/test", formerImage);
                    Files.deleteIfExists(filePath);
                }
                Files.copy(image.getInputStream(), Path.of("src/main/resources/static/fe/images/test/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                logger.info("image: {}", fileName);
                product.setImageUrl(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(this.productService.updateProduct(product)){
            return "redirect:/admin/quanLyProduct";
        }else{
            model.addAttribute("addProduct", addProduct);
            model.addAttribute("categories", categories);
            return "/admin/products/editProduct";
        }
    }

    @GetMapping("remove_product/{idProduct}")
    public String removeProduct(@PathVariable("idProduct") int id, Model model) {
        if (this.productService.deleteProductById(id)) {
            model.addAttribute("ThongBao", "đã xóa thành công");
        } else {
            model.addAttribute("ThongBao", "Không thể xóa mục này");
        }
        return "redirect:/admin/quanLyProduct";
    }

    @GetMapping("search_product")
    public String searchProduct(@RequestParam("search") String search, Model model){
        List<Product> products = this.productService.getAllProductByProductName(search);
        model.addAttribute("products",products);
        return "/admin/products/indexProduct";
    }
//    ĐÓNG PRODUCT

}

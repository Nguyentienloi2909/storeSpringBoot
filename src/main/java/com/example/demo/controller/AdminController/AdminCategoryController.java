package com.example.demo.controller.AdminController;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    public static final Logger logger = LoggerFactory.getLogger(AdminCategoryController.class);
    private CategoryService categoryService;
    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Category
    @GetMapping("quanLyCategory")
    public String quanLyMenu(Model model){
        List<Category> categories = categoryService.getALl();
        model.addAttribute("categories" , categories);
        return "/admin/category/indexCatetory";
    }

    @GetMapping("add_category")
    public String addCategory(Model model){
        Category category = new Category();
        category.setCategoryStatus(true);
        model.addAttribute("category",category);
        return "/admin/category/addCategory";
    }

    @PostMapping("add_category")
    public String process( @ModelAttribute("category") Category category,
                           Model model){
        if(category.getCategoryName().isEmpty() ){
            model.addAttribute("category",category);
            model.addAttribute("eCategory", "Thông tin bắt buộc");
            return "/admin/category/addCategory";
        }
        if(this.categoryService.create(category)){
            return "redirect:/admin/quanLyCategory";
        }else{
            model.addAttribute("category",category);
            return "/admin/category/addCategory";
        }
    }

    @GetMapping("edit_category/{idCategory}")
    public String editCategory(Model model, @PathVariable("idCategory") int id){
        Category category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "/admin/category/editCategory";
    }

    @PostMapping("edit_category")
    public String updateCategory(@ModelAttribute("category") Category category,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest httpServletRequest){
        logger.info("Category details before update: id={}, name={}, status={}",
                category.getIdCategory(), category.getCategoryName(), category.isCategoryStatus());
        if(category.getCategoryName().isEmpty() ){
            redirectAttributes.addFlashAttribute ("eCategory", "Thông tin bắt buộc");
            return "redirect:"+ httpServletRequest.getContextPath() + "/admin/edit_category/"+category.getIdCategory();
        }
        if(this.categoryService.update(category)){
            return "redirect:/admin/quanLyCategory";
        }else{
            return "redirect:/admin/edit_category/"+category.getIdCategory();
        }

    }

    @GetMapping("remove_category/{idCategory}")
    public String removeCategory(@PathVariable("idCategory") int id,
                                 Model model){
        if(this.categoryService.delete(id)){
            model.addAttribute("ThongBao", "đã xóa thành công");
        }else{
            model.addAttribute("ThongBao", "Không thể xóa mục này");
        }
        return "redirect:/admin/quanLyCategory";
    }


    @PostMapping("search_category")
    public String searchCategory(@RequestParam("search") String search, Model model){
        List<Category> categories = this.categoryService.findAllByCategoryName(search);
        model.addAttribute("categories", categories);
        return "/admin/category/indexCatetory";
    }
    // ĐÓNG CATEGORY

}

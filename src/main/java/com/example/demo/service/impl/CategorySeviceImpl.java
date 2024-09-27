package com.example.demo.service.impl;

import com.example.demo.dao.CategoryRepository;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategorySeviceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getALl() {
        return this.categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Boolean create(Category catetory) {
        try {
            this.categoryRepository.save(catetory);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Category findById(int id) {
        return this.categoryRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Boolean update(Category category) {
        if (this.categoryRepository.existsById(category.getIdCategory())) {
            this.categoryRepository.saveAndFlush(category);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean delete(int id) {
        try {
            this.categoryRepository.delete(this.categoryRepository.findById(id).get());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Category> findAllByCategoryName(String categoryName) {
        return this.categoryRepository.findAllByCategoryName(categoryName);
    }
}

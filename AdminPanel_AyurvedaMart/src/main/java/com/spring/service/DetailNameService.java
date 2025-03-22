package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.entity.Category;
import com.spring.entity.DetailName;
import com.spring.repo.CategoryRepository;
import com.spring.repo.DetailNameRepository;

@Service
public class DetailNameService {
	  private final DetailNameRepository repository;
	    private final CategoryRepository categoryRepository;

	    public DetailNameService(DetailNameRepository repository, CategoryRepository categoryRepository) {
	        this.repository = repository;
	        this.categoryRepository = categoryRepository;
	    }

    public DetailName addDetailName(DetailName detailName) {
        return repository.save(detailName);
    }
    public List<DetailName> addDetailNames(List<DetailName> detailNames, String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);

        if (category == null) {
            throw new RuntimeException("Category not found: " + categoryName);
        }
       
        detailNames.forEach(detailName -> detailName.setCategoryId(category.getId()));
        return repository.saveAll(detailNames);
    }
    
    public List<DetailName> getDetailNamesByCategoryName(String categoryName) {
    	Long categoryId=categoryRepository.findByCategoryName1(categoryName);
    	
        return repository.findByCategoryId(categoryId);
    }

	public List<DetailName> getDetailNamesByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		return repository.findByCategoryId(categoryId);
	}
}

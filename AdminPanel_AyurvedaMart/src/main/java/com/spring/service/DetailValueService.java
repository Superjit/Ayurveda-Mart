package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.entity.DetailName;
import com.spring.entity.DetailValue;
import com.spring.repo.CategoryRepository;
import com.spring.repo.DetailNameRepository;
import com.spring.repo.DetailValueRepository;

@Service
public class DetailValueService {
    private final DetailValueRepository repository;
    private final CategoryRepository categoryRepository;
    private final DetailNameRepository detailNameRepository;

    

    



	public DetailValueService(DetailValueRepository repository, CategoryRepository categoryRepository,
			DetailNameRepository detailNameRepository) {
		super();
		this.repository = repository;
		this.categoryRepository = categoryRepository;
		this.detailNameRepository = detailNameRepository;
	}







	// Handle adding multiple DetailValues
    public List<DetailValue> addDetailValues(List<DetailValue> detailValues) {
    	
    	
    	
    	
        return repository.saveAll(detailValues); // Save all the DetailValues at once
    }
}

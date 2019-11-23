package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.CategoryDto;
import pl.bpiotrowski.webstore.entity.Category;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(mapCategoryEntityToCategoryDto(category));
        }
        return dtos;
    }

    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category " + id + " not found"));
        return mapCategoryEntityToCategoryDto(category);
    }

    public void create(CategoryDto categoryDto) {
        categoryRepository.save(mapCategoryDtoToEntity(categoryDto));
    }

    private CategoryDto mapCategoryEntityToCategoryDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

    private Category mapCategoryDtoToEntity(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        return entity;
    }
}

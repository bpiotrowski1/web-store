package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.CategoryDto;
import pl.bpiotrowski.webstore.entity.Category;
import pl.bpiotrowski.webstore.repository.CategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void create(CategoryDto categoryDto) {
        categoryRepository.save(mapCategoryDtoToEntity(categoryDto));
    }

    private Category mapCategoryDtoToEntity(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        return entity;
    }
}

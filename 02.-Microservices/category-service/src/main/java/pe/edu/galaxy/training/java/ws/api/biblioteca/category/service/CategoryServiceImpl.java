package pe.edu.galaxy.training.java.ws.api.biblioteca.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.CategoryServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.mapper.CategoryMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.repository.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pe.edu.galaxy.training.java.ws.api.biblioteca.category.service.CategoryErrorMessages.*;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.category.service.CategoryHandlerException.handleException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Flux<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .filter(c -> "1".equals(c.getState()))
                .map(categoryMapper::toDto)
                .onErrorMap(e -> new CategoryServiceException(ERROR_LIST_CATEGORY, e));
    }

    @Override
    public Flux<CategoryResponse> findByName(String name) {
        return categoryRepository.findByName((name == null) ? "" : name.trim())
                .map(categoryMapper::toDto)
                .onErrorMap(e -> new CategoryServiceException(ERROR_FIND_CATEGORY_BY_NAME, e));
    }

    @Override
    public Flux<CategoryResponse> findByIds(java.util.List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }
        return categoryRepository.findByIdIn(ids)
                .map(categoryMapper::toDto)
                .onErrorMap(e -> new CategoryServiceException("Error fetching categories by multiple ids", e));
    }

    @Override
    public Mono<CategoryResponse> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .switchIfEmpty(Mono.error(
                        new CategoryServiceException(String.format(CATEGORY_NOT_FOUND_BY_ID, id))
                ))
                .onErrorMap(e -> {
                    if (e instanceof CategoryServiceException) return e;
                    return new CategoryServiceException(ERROR_FIND_CATEGORY_BY_ID, e);
                });
    }

    @Override
    public Mono<Void> save(CategoryRequest request) {
        var entity = categoryMapper.toEntity(request);
        entity.setState("1");
        return categoryRepository.save(entity)
                .then()
                .onErrorMap(e -> handleException(e, request, ERROR_SAVE_CATEGORY, null));
    }

    @Override
    public Mono<Void> update(Long id, CategoryRequest request) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new CategoryServiceException(String.format(CATEGORY_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setName(request.name());
                    entity.setDescription(request.description());
                    return categoryRepository.save(entity);
                })
                .then()
                .onErrorMap(e -> handleException(e, request, ERROR_UPDATE_CATEGORY, id));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new CategoryServiceException(String.format(CATEGORY_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> categoryRepository.deleteByIdCustom(id))
                .then()
                .onErrorMap(e -> new CategoryServiceException(String.format(ERROR_DELETE_CATEGORY, id), e));
    }
}

package pe.edu.galaxy.training.java.ws.api.biblioteca.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.ProductServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.entity.ProductEntity;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.mapper.ProductMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.product.service.ProductHandlerException.mapDuplicateConstraint;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Flux<ProductResponse> findAll() {
        return productRepository.findByName("")
                .map(productMapper::toDto)
            .onErrorMap(e -> new ProductServiceException("Error al listar los productos", e));
    }
    @Override
    public Mono<ProductResponse> findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
            .switchIfEmpty(Mono.error(
                new ProductServiceException(String.format("No existe producto con el id %s", id))
            ))
            .onErrorMap(e -> new ProductServiceException("Error al buscar producto por id", e));
    }

    @Override
    public Flux<ProductResponse> findByName(String name) {
        return productRepository.findByName( (name == null) ? "" : name.trim())
                .map(productMapper::toDto)
                .onErrorMap(e -> new ProductServiceException("Error al buscar productos por nombre", e));
    }

    @Override
    public Mono<ProductResponse> save(ProductRequest productRequest) {
        ProductEntity productEntity = productMapper.toEntity(productRequest);
        productEntity.setState("1");
        return productRepository.save(productEntity)
        .map(productMapper::toDto)
                .onErrorMap(ex -> {

                    String msg = ex.getMessage();
            if (msg != null) {
                if (msg.contains("uq_product_name")) {
                    return new DuplicateResourceException("name", productEntity.getName());
                }
            }
            return ex;
        });
    }

    @Override
    public Mono<ProductResponse> update(Long id, ProductRequest productRequest) {
        // Indepontente
    	 return productRepository.findById(id)
    		        .switchIfEmpty(Mono.error(
    		            new ProductServiceException(
    		                String.format("Producto no encontrado con id = %d", id))
    		        ))
    		        .flatMap(entity -> {
    		            entity.setName(productRequest.name());
    		            entity.setDescription(productRequest.description());
    		            return productRepository.save(entity)
                                .map(productMapper::toDto);
    		        })
    		        .onErrorMap(ex -> {
    		            Throwable throwable = mapDuplicateConstraint(
    		                    ex,
    		                    productRequest.name());
    		            if (throwable instanceof DuplicateResourceException) {
    		                return throwable;
    		            }
    		            return new ProductServiceException(
    		                String.format("Error al actualizar el producto con id = %d", id),
    		                ex);
    		        });
    }


    @Override
    public Mono<Void> delete(Long id) {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new ProductServiceException(String.format("No existe producto con el id %s", id))
            ))
            .flatMap(l -> productRepository.deleteByIdCustom(id))
            .then()
            .onErrorMap(e -> new ProductServiceException(String.format("Error al eliminar el producto, no existe producto con el id = %d",id), e));
    }

}

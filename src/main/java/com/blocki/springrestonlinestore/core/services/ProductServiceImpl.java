package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.CategoryMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.ProductRepository;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    private final ProductResourceAssembler productResourceAssembler;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductResourceAssembler productResourceAssembler, UserRepository userRepository) {

        this.productRepository = productRepository;
        this.productResourceAssembler = productResourceAssembler;
        this.userRepository = userRepository;
    }

    @Override
    public Resource<ProductDTO> getProductById(Long id) {

        return productRepository
                .findById(id)
                .map(productConverter::productToProductDTO)
                .map(productResourceAssembler::toResource)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Resource<ProductDTO> getProductByName(String name) {

        return productRepository
                .findProductByName(name)
                .map(productConverter::productToProductDTO)
                .map(productResourceAssembler::toResource)
                .orElseThrow(NotFoundException::new);
    }

    private Resource<ProductDTO> saveProduct(ProductDTO productDTO) {

        Product product = productRepository.save(productConverter.productDTOToProduct(productDTO));

        return productResourceAssembler.toResource(productConverter.productToProductDTO(product));
    }

    @Override
    public Resource<ProductDTO> patchProduct(Long id, ProductDTO productDTO) {

        return productRepository
                .findById(id)
                .map(product -> {

                    if(productDTO.getCost() != null) {

                        product.setCost(productDTO.getCost());
                    }

                    if(productDTO.getCreationDate() != null) {

                        product.setCreationDate(productDTO.getCreationDate());
                    }

                    if(productDTO.getDescription() != null) {

                        product.setDescription(productDTO.getDescription());
                    }

                    if(productDTO.getName() != null) {

                        product.setName(productDTO.getName());
                    }

                    if(productDTO.getPhoto() != null) {

                        product.setPhoto(productDTO.getPhoto());
                    }

                    if(productDTO.getProductStatus() != null) {

                        product.setProductStatus(productDTO.getProductStatus());
                    }

                    if(productDTO.getCategoryDTO() != null) {

                        product.setCategory(categoryConverter.categoryDTOtoCategory(productDTO.getCategoryDTO()));
                    }

                    return saveProduct(productConverter.productToProductDTO(product));

                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()) {

           User user = userRepository.findById(product.get().getUser().getId()).orElseThrow(NotFoundException::new);

           user.getProducts().remove(product.get());
           userRepository.save(user);

        }

        productRepository.deleteById(id);
    }
}

package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.CategoryMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductListDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductListDTO getAllProducts() {

        List<ProductDTO> ProductDTOs = new ArrayList<>();

        for(Product shoppingCart : productRepository.findAll()) {

            ProductDTO productDTO = productConverter.productToProductDTO(shoppingCart);
            ProductDTOs.add(productDTO);
        }

        return new ProductListDTO(ProductDTOs);
    }

    @Override
    public ProductDTO getProductById(Long id) {

        return productRepository
                .findById(id)
                .map(productConverter::productToProductDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {

        Product product = productRepository.save(productConverter.productDTOToProduct(productDTO));

        return productConverter.productToProductDTO(product);
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {

        return saveProduct(productDTO);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {

        Product product = productConverter.productDTOToProduct(productDTO);
        product.setId(id);

        return saveProduct(productConverter.productToProductDTO(product));
    }

    @Override
    public ProductDTO patchProduct(Long id, ProductDTO productDTO) {

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

                    if(productDTO.getUserDTO() != null) {

                      product.setUser(userConverter.userDTOToUser(productDTO.getUserDTO()));
                    }

                    return saveProduct(productConverter.productToProductDTO(product));

                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteProductById(Long id) {

        productRepository.deleteById(id);

    }
}

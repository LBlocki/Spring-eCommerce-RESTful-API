package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductListDTO;

public interface ProductService {

    ProductListDTO getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO saveProduct(ProductDTO shoppingCartDTO);

    ProductDTO createNewProduct(ProductDTO shoppingCartDTO);

    ProductDTO updateProduct(Long id, ProductDTO shoppingCartDTO);

    ProductDTO patchProduct(Long id, ProductDTO shoppingCartDTO);

    void deleteProductById(Long id);
}

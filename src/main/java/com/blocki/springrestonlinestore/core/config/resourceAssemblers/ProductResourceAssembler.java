package com.blocki.springrestonlinestore.core.config.resourceAssemblers;

import com.blocki.springrestonlinestore.api.v1.controllers.ProductController;
import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler  implements ResourceAssembler<ProductDTO, Resource<ProductDTO>> {

    @Override
    public Resource<ProductDTO> toResource(ProductDTO productDTO) {

       return new Resource<>(productDTO,
               linkTo(methodOn(ProductController.class).getProductById(productDTO.getId()))
                       .withSelfRel().withType("GET"),
               linkTo(methodOn(ProductController.class).getProductByName(productDTO.getName())).
                       withRel("get product by name").withType("GET"),
               linkTo(methodOn(ProductController.class).patchProduct(productDTO.getUserDTO().getId(), productDTO))
                       .withRel("update product").withType("PATCH"),
               linkTo(methodOn(ProductController.class).getProductById(productDTO.getUserDTO().getId()))
                       .withRel("delete product").withType("DELETE"),
               linkTo(methodOn(UserController.class).getAllUsersProducts(productDTO.getUserDTO().getId()))
                       .withRel("get list of users products").withType("GET"));
    }
}

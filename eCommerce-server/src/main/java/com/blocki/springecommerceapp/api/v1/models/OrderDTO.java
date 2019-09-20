package com.blocki.springecommerceapp.api.v1.models;

import com.blocki.springecommerceapp.core.config.parsers.LocalDateDeserializer;
import com.blocki.springecommerceapp.core.config.parsers.LocalDateSerializer;
import com.blocki.springecommerceapp.core.domain.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(value = "order", collectionRelation = "orders")
public class OrderDTO {

    private Long id;

    @JsonProperty("user_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDTO userDTO;

    @NotNull(message = "Owner_id field cannot be null")
    @JsonProperty("owner_id")
    private Long userDTOId;

    @JsonProperty("order_items")
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

    @NotNull(message = "Creation date field value cannot be null")
    @JsonProperty("creation_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;

    @JsonProperty("order_status")
    @NotNull(message = "Order status field value cannot be null")
    private Order.OrderStatus orderStatus;

}

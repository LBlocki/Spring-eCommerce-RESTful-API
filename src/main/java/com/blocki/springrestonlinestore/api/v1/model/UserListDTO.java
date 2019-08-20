package com.blocki.springrestonlinestore.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {

    @JsonProperty("users")
    private List<UserDTO> userDTOs = new ArrayList<>();
}

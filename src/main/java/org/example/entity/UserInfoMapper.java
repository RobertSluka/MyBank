package org.example.entity;

import org.example.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {
    public User toEntity(UserDTO dto){
    User entity = new User();
    entity.setName(dto.getName());
    entity.setPhone(dto.getPhone());
    entity.setAccountNumber(dto.getAccountNumber());
    entity.setAccountStatus(Double.valueOf(dto.getAccountStatus()));
    return entity;
}
}

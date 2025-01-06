package com.sumit.api;

import com.sumit.model.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

@Component
public class UserResourceAssembler {

  private final ModelMapper modelMapper;
  private final IdGenerator idGenerator;

  public UserResourceAssembler(ModelMapper modelMapper, IdGenerator idGenerator) {
    this.modelMapper = modelMapper;
    this.idGenerator = idGenerator;
  }

  public UserResource toResource(Users user) {
    return modelMapper.map(user, UserResource.class);
  }

  public Users toModel(UserResource userResource) {
    Users user = modelMapper.map(userResource, Users.class);
    if (user.getId() == null) {
      user.setId(idGenerator.generateId());
    }
    return user;
  }
}

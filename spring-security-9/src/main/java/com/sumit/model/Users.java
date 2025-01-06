package com.sumit.model;

import com.sumit.common.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table("users")
public class Users {

  @Id
  private UUID id;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private List<Role> roles;

  public Users() {}

  public Users(Users user) {
    this(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getFirstName(),
            user.getLastName(),
            user.getRoles());
  }

  @PersistenceConstructor
  public Users(
          UUID id, String email, String password, String firstName, String lastName, List<Role> roles) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.roles = roles;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Users user = (Users) o;
    return id.equals(user.id)
            && email.equals(user.email)
            && password.equals(user.password)
            && firstName.equals(user.firstName)
            && lastName.equals(user.lastName)
            && roles.equals(user.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, password, firstName, lastName, roles);
  }

  @Override
  public String toString() {
    return "Users{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", roles=" + roles +
            '}';
  }
}



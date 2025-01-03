package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.userAuth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String string);

    List<Role> findByIdIn(List<Long> ids);
}

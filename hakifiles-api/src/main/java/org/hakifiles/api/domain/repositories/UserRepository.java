package org.hakifiles.api.domain.repositories;

import org.hakifiles.api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

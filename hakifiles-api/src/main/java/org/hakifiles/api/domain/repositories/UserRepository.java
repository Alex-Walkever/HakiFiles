package org.hakifiles.api.domain.repositories;

import org.hakifiles.api.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
//    @Query("SELECT * FROM User u ORDER BY u.id LIMIT ?1 OFFSET ?2")
//    List<User> getUserPerPagination(Integer limit, Integer offset);
}

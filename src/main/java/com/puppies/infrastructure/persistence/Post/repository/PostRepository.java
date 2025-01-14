package com.puppies.infrastructure.persistence.Post.repository;

import com.puppies.domain.Post.entity.Post;
import com.puppies.domain.User.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<Post, UUID> {

    List<Post> findByUserNotOrderByDateDesc(User user);
    List<Post> findByUserOrderByDateDesc(User user);
}

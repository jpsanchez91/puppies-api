package com.puppies.infrastructure.persistence.Like.repository;

import com.puppies.domain.Like.entity.Like;
import com.puppies.domain.Post.entity.Post;
import com.puppies.domain.User.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository  extends CrudRepository<Like, UUID> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByUser(User user);
}

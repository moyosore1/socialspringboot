package com.moyosore.socialspring.post;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
  // a spring data repository method.

  Optional<Post> findBySlug(UUID uuid);

  List<Post> findByAuthorId(Long id);


}

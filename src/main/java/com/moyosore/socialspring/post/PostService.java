package com.moyosore.socialspring.post;


import com.moyosore.socialspring.exception.ApiRequestException;
import com.moyosore.socialspring.user.AppUser;
import com.moyosore.socialspring.user.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final static String USER_NOT_FOUND_MSG = "User does not exist";

  public Post addNewPost(Post post, Principal user) throws ApiRequestException{
    UUID postId = UUID.randomUUID();
    if(postRepository.findBySlug(postId).isPresent()){
      System.out.println("Present");
    }
    AppUser appUser = userRepository.findByEmail(user.getName())
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG)));

    post.setSlug(postId);
    post.setAuthor(appUser);
    return postRepository.save(post);
  }

  public List<Post> getUsersPosts(Long id){
    if(!userRepository.existsById(id)){
      throw new ApiRequestException("Invalid user id");
    }
    return postRepository.findByAuthorId(id);
  }

  public Post getAPost(UUID postSlug){
    Post post = postRepository.findBySlug(postSlug).orElseThrow(() -> new ApiRequestException("Post with slug:" +postSlug+ " does not exist"));
    return post;
  }




}

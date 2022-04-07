package com.moyosore.socialspring.comment;


import com.moyosore.socialspring.exception.ApiRequestException;
import com.moyosore.socialspring.post.Post;
import com.moyosore.socialspring.post.PostRepository;
import com.moyosore.socialspring.user.AppUser;
import com.moyosore.socialspring.user.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final static String USER_NOT_FOUND_MSG = "User does not exist";

  public List<Comment> getCommentsForPost(Long postId) {
    if (!postRepository.existsById(postId)) {
      throw new ApiRequestException("Post with id:" + postId + " does not exist.");
    }
    return commentRepository.findByPostId(postId);
  }

  public Comment createComment(Long postId, Comment comment, Principal user) {
    Optional<Post> post = postRepository.findById(postId);
    if (post.isEmpty()) {
      throw new ApiRequestException("Post with id:" + postId + " does not exist.");
    }
    AppUser appUser = userRepository.findByEmail(user.getName())
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG)));
    comment.setPost(post.get());
    comment.setUserComment(appUser);
    return commentRepository.save(comment);
  }

  public Comment updateComment(Comment comment, Principal user) {
    Comment commentObj = commentRepository.findById(comment.getId()).orElseThrow(() -> new ApiRequestException("Comment with id: "+ comment.getId()+" does not exist"));

    AppUser appUser = userRepository.findByEmail(user.getName())
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG)));
    if (commentObj.getUserComment() == appUser) {
      comment.setUserComment(appUser);
      comment.setPost(commentObj.getPost());
      return commentRepository.save(comment);
    }
    throw new ApiRequestException(
        "Either comment does not exist or current user did not create comment.");
  }

  public void deleteComment(Long id, Principal user) {
    Optional<Comment> commentObj = commentRepository.findById(id);
    System.out.println("Comment obj "+commentObj.get());
    System.out.println("Comment user " +commentObj.get().getUserComment());
    AppUser appUser = userRepository.findByEmail(user.getName())
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG)));
    System.out.println(appUser);
    System.out.println(commentObj.get().getUserComment() == appUser);
    if (commentObj.isPresent() && commentObj.get().getUserComment() == appUser) {
      commentRepository.deleteById(id);
      return;
    }else {
      throw new ApiRequestException("Could not delete comment.");
    }

  }

  public Comment getComment(Long id) {
    Optional<Comment> comment = commentRepository.findById(id);
    if (comment.isEmpty()) {
      throw new ApiRequestException("Comment with id: " + id + " does not exist.");
    }
    return comment.get();
  }


}

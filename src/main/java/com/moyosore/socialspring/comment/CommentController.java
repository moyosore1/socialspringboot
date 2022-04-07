package com.moyosore.socialspring.comment;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

  private final CommentService commentService;
  private final ModelMapper modelMapper;

  @Autowired
  public CommentController(CommentService commentService, ModelMapper modelMapper) {
    this.commentService = commentService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/{postId}")
  public ResponseEntity<List<CommentDTO>> getPostComments(@PathVariable("postId") Long postId) {
    List<CommentDTO> commentDTO = commentService.getCommentsForPost(postId).stream()
        .map(comment -> modelMapper.map(comment, CommentDTO.class)).collect(
            Collectors.toList());
    return new ResponseEntity<List<CommentDTO>>(commentDTO, HttpStatus.OK);
  }

  @PostMapping("/{postId}")
  public ResponseEntity<CommentDTO> saveComment(@RequestBody @Valid Comment comment,
      @PathVariable("postId") Long postId, Principal user) {
    Comment newComment = commentService.createComment(postId, comment, user);
    CommentDTO commentDTO = modelMapper.map(newComment, CommentDTO.class);
    return new ResponseEntity<CommentDTO>(commentDTO,
        HttpStatus.CREATED);
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<CommentDTO> updateComment(@RequestBody @Valid Comment comment,
      @PathVariable("commentId") Long commentId, Principal user) {
    comment.setId(commentId);
    Comment updatedComment = commentService.updateComment(comment, user);
    CommentDTO commentDTO = modelMapper.map(updatedComment, CommentDTO.class);
    return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId,
      Principal user) {
    commentService.deleteComment(commentId, user);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/get/{commentId}")
  public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId) {
    Comment comment = commentService.getComment(commentId);
    CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
    return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
  }


}

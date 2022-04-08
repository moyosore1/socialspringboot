package com.moyosore.socialspring.post;


import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

  private final PostService postService;
  private final ModelMapper modelMapper;

  @Autowired
  public PostController(PostService postService, ModelMapper modelMapper) {
    this.postService = postService;
    this.modelMapper = modelMapper;
  }


  @PostMapping
  public ResponseEntity<PostResponseDTO> createPosts(@RequestBody @Valid Post post, Principal user) {
    System.out.println("got here");
    Post newPost = postService.addNewPost(post, user);
    PostResponseDTO postResponse = modelMapper.map(post, PostResponseDTO.class);
    return new ResponseEntity<PostResponseDTO>(postResponse, HttpStatus.CREATED);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<List<PostResponseDTO>> getUsersPosts(@PathVariable Long id) {
    List<PostResponseDTO> postDTO = postService.getUsersPosts(id).stream()
        .map(post -> modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
    return new ResponseEntity<List<PostResponseDTO>>(postDTO, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponseDTO> getAPost(@PathVariable("id") UUID id){
    Post post = postService.getAPost(id);
    PostResponseDTO responseDTO = modelMapper.map(post, PostResponseDTO.class);
    return new ResponseEntity<PostResponseDTO>(responseDTO, HttpStatus.OK);
  }


}

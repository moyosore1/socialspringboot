package com.moyosore.socialspring.comment;

import com.moyosore.socialspring.user.UserDTO;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class CommentDTO {

  private Long id;
  private String content;
  private UUID postSlug;
  private UserDTO userComment;
  private Date createdAt;
  private Date updatedAt;

}

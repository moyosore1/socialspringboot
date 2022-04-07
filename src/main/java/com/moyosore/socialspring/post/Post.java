package com.moyosore.socialspring.post;

import com.moyosore.socialspring.comment.Comment;
import com.moyosore.socialspring.user.AppUser;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Setter
@Getter
@NoArgsConstructor
@Table(name = "Posts")
@Entity
public class Post {

  @Id
  @SequenceGenerator(
      name = "post_sequence",
      sequenceName = "post_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
  private Long id;
  @NotBlank(message = "Title is required.")
  private String title;

  @Column(columnDefinition = "TEXT")
  @NotBlank(message = "Content is required.")
  private String content;


  private UUID slug;

  @CreationTimestamp
  @Temporal(value = TemporalType.DATE)
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;


  @UpdateTimestamp
  @Temporal(value = TemporalType.DATE)
  @Column(name = "updated_at")
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "app_user_id", nullable = false)
  private AppUser author;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments;


}

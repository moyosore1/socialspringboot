package com.moyosore.socialspring.comment;


import com.moyosore.socialspring.post.Post;
import com.moyosore.socialspring.user.AppUser;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
public class Comment {

  @SequenceGenerator(
      name = "comment_sequence",
      sequenceName = "comment_sequence",
      allocationSize = 1
  )
  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "comment_sequence"
  )
  private Long id;

  @Column(columnDefinition = "TEXT")
  @NotBlank(message = "Content is required.")
  private String content;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @ManyToOne
  @JoinColumn(name = "app_user_id", nullable = false)
  private AppUser userComment;

  @ManyToMany
  @JoinTable(
      name = "comment_likes",
      joinColumns = @JoinColumn(name = "comment_id"),
      inverseJoinColumns = @JoinColumn(name = "app_user_id")
  )
  private Set<AppUser> likes;

  @CreationTimestamp
  @Temporal(value = TemporalType.DATE)
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(value = TemporalType.DATE)
  @Column(name = "updated_at")
  private Date updatedAt;
}

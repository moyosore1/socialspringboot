package com.moyosore.socialspring.friendrequest;

import com.moyosore.socialspring.user.AppUser;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
public class FriendRequest {

  @Id
  @SequenceGenerator(
      name = "friend_request_sequence",
      sequenceName = "friend_request_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_request_sequence")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private AppUser sender;

  @ManyToOne
  @JoinColumn(name="receiver_id")
  private AppUser receiver;

  private Boolean is_active;

  @CreationTimestamp
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date timestamp;


  public FriendRequest(AppUser sender, AppUser receiver) {
    this.sender = sender;
    this.receiver = receiver;
    this.is_active = true;
  }
}

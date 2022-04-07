package com.moyosore.socialspring.friends;


import com.moyosore.socialspring.user.AppUser;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friend {

  @Id
  @SequenceGenerator(
      name = "post_sequence",
      sequenceName = "post_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
  private Long id;

  @OneToOne
  @JoinColumn(name = "app_user_id")
  private AppUser user;

  @ManyToMany
  @JoinTable(
      name = "friend_user",
      joinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id")
  )
  private List<AppUser> friends;

  public boolean addFriend(AppUser user){
    for(AppUser friend: this.getFriends()){
      if(user == friend){
        return false;
      }
    }
    boolean sth = friends.add(user);
    System.out.println(sth);
    return sth;
  }

  public boolean removeFriend(AppUser user){
    for(AppUser exFriend: this.getFriends()){
      if(user == exFriend){
        boolean sth = friends.remove(user);
        System.out.println(sth);
        return sth;
      }
    }
    return false;
  }

//  public void unfriend(AppUser user){
//    // person terminating friendship
//    Friend remover = this;
//    // removes friend from remover's list
//    this.removeFriend(user);
//
//    // Removes friend from person being removed list
//    Friend friend_list =
//
//  }


}

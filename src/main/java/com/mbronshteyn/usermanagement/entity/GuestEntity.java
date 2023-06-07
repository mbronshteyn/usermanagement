package com.mbronshteyn.usermanagement.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "guests", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"firstName", "lastName"})
})
public class GuestEntity implements Serializable {

  @Id
  @GeneratedValue
  private long id;

  @Column(nullable = false)
  private String batchNumber;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @OneToMany(mappedBy = "guests", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ClubEntity> clubs;
}

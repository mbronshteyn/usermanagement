package com.mbronshteyn.usermanagement.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "clubs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class ClubEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity users;

    @ManyToOne
    @JoinColumn(name = "guests_id")
    private GuestEntity guests;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "themes_id")
    private ThemeEntity theme;

}

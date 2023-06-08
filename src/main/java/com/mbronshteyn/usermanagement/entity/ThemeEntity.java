package com.mbronshteyn.usermanagement.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "themes")
public class ThemeEntity implements Serializable {

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    List<ClubEntity> clubs;
    @Enumerated(EnumType.STRING)
    ThemeEnum themeEnum;
    @Id
    @GeneratedValue
    private long id;
}

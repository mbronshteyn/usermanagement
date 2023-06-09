package com.mbronshteyn.usermanagement.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "themes")
public class ThemeEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    ThemeEnum name;
    @Id
    @GeneratedValue
    private long id;
}

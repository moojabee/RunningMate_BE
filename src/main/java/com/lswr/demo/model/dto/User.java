package com.lswr.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String address;
    private LocalDateTime  createdDate;
    private boolean isPrivate;
}

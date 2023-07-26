package com.hms.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class UserDetailsDto {
    private long id;
    private String name;
    private int age;
    private String phoneNumber;
    private String address;

    private String userName;
}

package model.entity;

import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String contactNumber;
    private String nic;
    private LocalDate membershipDate;
}

package model.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class IssuedBookEntity {
    private Integer issuedId;
    private Integer memberId;
    private Integer bookId;
    private LocalDate issuedDate;
    private LocalDate returnedDate;
    private Double fine;
    private String status;
}

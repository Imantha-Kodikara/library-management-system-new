package model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBookDTO {
    private Integer issuedId;
    private Integer memberId;
    private Integer bookId;
    private LocalDate issuedDate;
    private LocalDate returnedDate;
    private Double fine;
    private String status;
}

package model.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnBookEntity {
    private Integer issuedId;
    private Integer memberId;
    private Integer bookId;
    private Double fine;
    private LocalDate returnedDate;
    private String status;
}

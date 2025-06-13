package model.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookEntity {
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private Integer noOfCopies;
}

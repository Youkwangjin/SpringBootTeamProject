package pack.dto.review;

import lombok.Data;

@Data
public class ReviewDTO {
    private int rating, cont_no;
    private String content, user_id;
}

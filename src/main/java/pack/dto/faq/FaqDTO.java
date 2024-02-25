package pack.dto.faq;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaqDTO {
    private String faq_no, faq_category, faq_question, faq_answer, detail;
    private String searchName, searchValue;
    private int num;
}

package pack.dto.faq;

import lombok.Data;

@Data
public class FaqDTO {
	private String faq_no, faq_category, faq_question, faq_answer;
	private String searchName, searchValue;
	private int num;
}

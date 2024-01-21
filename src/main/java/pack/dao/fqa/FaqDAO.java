package pack.dao.fqa;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.faq.FaqDTO;
import pack.mapper.fqa.FaqMapper;


@Repository
@AllArgsConstructor
public class FaqDAO {

	private final FaqMapper faqMapper;
	
	public List<FaqDTO> listFaq(){
        return faqMapper.selectFaq();
	}
	
	public List<FaqDTO> searchFaq(FaqDTO faqDTO){
        return faqMapper.searchFaq(faqDTO);
	}
	
	public int totalFaq() {
		return faqMapper.totalFaq();
	}
	
	@Transactional
	public boolean insertFaq(FaqDTO faqDTO) {
		boolean b = false;
		int insertFaqRe = faqMapper.insertFaq(faqDTO);
		if(insertFaqRe > 0) b = true;
		return b;
	}
	
	@Transactional
	public boolean updateFaq(FaqDTO faqDTO) {
		boolean b = false;
		int updateFaqRe = faqMapper.updateFaq(faqDTO);
		if(updateFaqRe > 0) b = true;
		return b;
	}
	
	@Transactional
	public boolean deleteFaq(String faq_no) {
		boolean b = false;
		int deleteFaqRe = faqMapper.deleteFaq(faq_no);
		if(deleteFaqRe > 0) b = true;
		return b;
	}
	
	public FaqDTO detailFaq(int faq_no) {
        return faqMapper.selectNo(faq_no);
	}
}

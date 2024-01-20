package pack.dao.fqa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.faq.FaqDTO;
import pack.mapper.fqa.FaqMapper;


@Repository
public class FaqDAO {

	@Autowired
	private FaqMapper faqMapper;
	
	public List<FaqDTO> listFaq(){
		List<FaqDTO> list = faqMapper.selectFaq();
		return list;
	}
	
	public List<FaqDTO> searchFaq(FaqDTO faqDTO){
		List<FaqDTO> sList = faqMapper.searchFaq(faqDTO);
		return sList;
	}
	
	public int totalFaq() {
		return faqMapper.totalFaq();
	}
	
	@Transactional
	public boolean insertFaq(FaqDTO faqDTO) {
		boolean b = false;
		int re = faqMapper.insertFaq(faqDTO);
		if(re>0) b = true;
		return b;
	}
	
	@Transactional
	public boolean updateFaq(FaqDTO faqDTO) {
		boolean b = false;
		int re = faqMapper.updateFaq(faqDTO);
		if(re>0) b = true;
		return b;
	}
	
	@Transactional
	public boolean deleteFaq(String faq_no) {
		boolean b = false;
		int re = faqMapper.deleteFaq(faq_no);
		if(re>0) b = true;
		return b;
	}
	
	public FaqDTO detailFaq(int faq_no) {
		FaqDTO detail = faqMapper.selectno(faq_no);
		return detail;
	}
}

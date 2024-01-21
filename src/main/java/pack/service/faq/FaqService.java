package pack.service.faq;

import org.springframework.stereotype.Service;
import pack.dto.faq.FaqDTO;

import java.util.List;

@Service
public interface FaqService {
    List<FaqDTO> listFaq();
    List<FaqDTO> searchFaq(FaqDTO faqDTO);
    int totalFaq();
    boolean insertFaq(FaqDTO faqDTO);
    boolean updateFaq(FaqDTO faqDTO);
    boolean deleteFaq(String faq_no);
    FaqDTO detailFaq(int faq_no);
    List<FaqDTO> getListData(List<FaqDTO> list, int page);
    int getPageSu();
    int getSearchPageSu(FaqDTO faqDTO);
}

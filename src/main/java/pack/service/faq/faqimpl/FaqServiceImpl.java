package pack.service.faq.faqimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dao.fqa.FaqDAO;
import pack.dto.faq.FaqDTO;
import pack.service.faq.FaqService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
    private final FaqDAO faqDao;
    private final int pList = 10;

    @Override
    public List<FaqDTO> listFaq() {
        return faqDao.listFaq();
    }

    @Override
    public List<FaqDTO> searchFaq(FaqDTO faqDTO) {
        return faqDao.searchFaq(faqDTO);
    }

    @Override
    public int totalFaq() {
        return faqDao.totalFaq();
    }

    @Transactional
    @Override
    public boolean insertFaq(FaqDTO faqDTO) {
        return faqDao.insertFaq(faqDTO);
    }

    @Transactional
    @Override
    public boolean updateFaq(FaqDTO faqDTO) {
        return faqDao.updateFaq(faqDTO);
    }

    @Transactional
    @Override
    public boolean deleteFaq(String faq_no) {
        return faqDao.deleteFaq(faq_no);
    }

    @Override
    public FaqDTO detailFaq(int faq_no) {
        return faqDao.detailFaq(faq_no);
    }

    @Override
    public List<FaqDTO> getListData(List<FaqDTO> list, int page) {
        List<FaqDTO> result = new ArrayList<>();
        int start = (page - 1) * pList;
        int end = Math.min(start + pList, list.size());
        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public int getPageSu() {
        int tot = this.totalFaq();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }

    @Override
    public int getSearchPageSu(FaqDTO faqDTO) {
        List<FaqDTO> searchTot = this.searchFaq(faqDTO);
        int tot = searchTot.size();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }
}

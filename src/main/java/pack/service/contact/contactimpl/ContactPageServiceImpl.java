package pack.service.contact.contactimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactPageService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactPageServiceImpl implements ContactPageService {

    private final ContactDAO contactDao;

    @Override
    public ArrayList<ContactDTO> getListData(ArrayList<ContactDTO> list, int page, int pList) {
        ArrayList<ContactDTO> result = new ArrayList<>();
        int start = (page - 1) * pList;
        int end = Math.min(start + pList, list.size());
        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public int getPageSu(int pList) {
        int tot = contactDao.totalContact();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }

    @Override
    public List<ContactDTO> listContact() {
        return contactDao.listContact();
    }
}

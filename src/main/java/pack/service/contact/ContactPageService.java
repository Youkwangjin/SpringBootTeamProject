package pack.service.contact;

import org.springframework.stereotype.Service;
import pack.dto.contact.ContactDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public interface ContactPageService {
    ArrayList<ContactDTO> getListData(ArrayList<ContactDTO> list, int page, int pList);
    int getPageSu(int pList);
    List<ContactDTO> listContact();
}
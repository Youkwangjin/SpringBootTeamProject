package pack.service.contact.contactImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactDetailService;
@Service
@AllArgsConstructor
public class ContactDetailServiceImpl implements ContactDetailService {

    private final ContactDAO contactDao;

    @Override
    public ContactDTO detailContact(String contact_no) {
        return contactDao.detailContact(contact_no);
    }
}


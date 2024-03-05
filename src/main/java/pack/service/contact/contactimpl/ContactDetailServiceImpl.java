package pack.service.contact.contactimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactDetailService;
@Service
@RequiredArgsConstructor
public class ContactDetailServiceImpl implements ContactDetailService {

    private final ContactDAO contactDao;

    @Override
    public ContactDTO detailContact(String contact_no) {
        return contactDao.detailContact(contact_no);
    }
}


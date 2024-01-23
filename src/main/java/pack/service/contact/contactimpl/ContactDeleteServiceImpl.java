package pack.service.contact.contactimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.service.contact.ContactDeleteService;

@Service
@AllArgsConstructor
public class ContactDeleteServiceImpl implements ContactDeleteService {

    private final ContactDAO contactDao;

    @Override
    public boolean deleteContact(String contact_no) {
        return contactDao.deleteContact(contact_no);
    }
}
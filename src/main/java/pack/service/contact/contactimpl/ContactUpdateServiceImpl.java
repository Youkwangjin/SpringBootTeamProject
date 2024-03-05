package pack.service.contact.contactimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactUpdateService;
@Service
@RequiredArgsConstructor
public class ContactUpdateServiceImpl implements ContactUpdateService {

    private final ContactDAO contactDao;

    @Override
    public boolean updateContact(ContactDTO contactDTO) {
        return contactDao.updateContact(contactDTO);
    }
}


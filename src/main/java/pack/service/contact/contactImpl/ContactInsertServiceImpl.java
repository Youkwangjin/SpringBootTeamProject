package pack.service.contact.contactImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactInsertService;
@Service
@AllArgsConstructor
public class ContactInsertServiceImpl implements ContactInsertService {

    private final ContactDAO contactDao;

    @Override
    public boolean insertContact(ContactDTO contactDTO) {
        return contactDao.insertContact(contactDTO);
    }
}
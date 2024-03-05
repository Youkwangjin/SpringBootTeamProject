package pack.service.contact.contactimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactInsertService;
@Service
@RequiredArgsConstructor
public class ContactInsertServiceImpl implements ContactInsertService {

    private final ContactDAO contactDao;

    @Override
    public boolean insertContact(ContactDTO contactDTO) {
        return contactDao.insertContact(contactDTO);
    }
}
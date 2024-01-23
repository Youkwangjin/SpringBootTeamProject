package pack.service.contact.contactImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactService;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactDAO contactDao;

    @Override
    public List<ContactDTO> listContact() {
        return contactDao.listContact();
    }
}

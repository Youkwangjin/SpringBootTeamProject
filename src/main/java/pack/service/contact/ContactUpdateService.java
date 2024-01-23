package pack.service.contact;

import org.springframework.stereotype.Service;
import pack.dto.contact.ContactDTO;

@Service
public interface ContactUpdateService {
    boolean updateContact(ContactDTO contactDTO);
}


package pack.service.contact;

import org.springframework.stereotype.Service;
import pack.dto.contact.ContactDTO;

@Service
public interface ContactDetailService {
    ContactDTO detailContact(String contact_no);
}

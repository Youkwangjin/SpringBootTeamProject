package pack.service.contact;

import org.springframework.stereotype.Service;
import pack.dto.contact.ContactDTO;
import java.util.List;


@Service
public interface ContactService {
    List<ContactDTO> listContact();
}

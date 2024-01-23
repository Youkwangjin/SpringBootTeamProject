package pack.service.contact;

import org.springframework.stereotype.Service;

@Service
public interface ContactDeleteService {
    boolean deleteContact(String contact_no);
}

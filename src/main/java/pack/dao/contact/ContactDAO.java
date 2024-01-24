package pack.dao.contact;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.contact.ContactDTO;
import pack.mapper.contact.ContactMapper;

@Repository
@AllArgsConstructor
public class ContactDAO {

    private final ContactMapper contactMapper;

    public List<ContactDTO> listContact() {
        return contactMapper.selectContact();
    }

    public int totalContact() {
        return contactMapper.totalContact();
    }

    @Transactional
    public boolean insertContact(ContactDTO contactDTO) {
        boolean insertContactData = false;
        int insertContactRe = contactMapper.insertContact(contactDTO);
        if (insertContactRe > 0) insertContactData = true;
        return insertContactData;
    }

    public ContactDTO detailContact(String contact_no) {
        return contactMapper.detailContact(contact_no);
    }

    @Transactional
    public boolean updateContact(ContactDTO contactDTO) {
        boolean updateContactData = false;
        int updateContactRe = contactMapper.updateContact(contactDTO);
        if (updateContactRe > 0) updateContactData = true;
        return updateContactData;
    }

    @Transactional
    public boolean deleteContact(String contact_no) {
        boolean deleteContactData = false;
        int deleteContactRe = contactMapper.deleteContact(contact_no);
        if (deleteContactRe > 0) deleteContactData = true;
        return deleteContactData;
    }
}

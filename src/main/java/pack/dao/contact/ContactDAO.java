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
	
	public List<ContactDTO> listContact(){
		List<ContactDTO> list = contactMapper.selectContact();
		System.out.println(list);
		return list;
	}
	
	public int totalContact() {
		return contactMapper.totalContact();
	}
	
	@Transactional
	public boolean insertContact(ContactDTO contactDTO) {
		boolean b = false;
		int re = contactMapper.insertContact(contactDTO);
		if(re>0) b = true;
		return b;
	}
	
	public ContactDTO detailContact(String contact_no) {
        return contactMapper.detailContact(contact_no);
	}
	
	@Transactional
	public boolean updateContact(ContactDTO contactDTO) {
		boolean b = false;
		int re = contactMapper.updateContact(contactDTO);
		if(re>0) b = true;
		return b;
	}
	
	@Transactional
	public boolean deleteContact(String contact_no) {
		boolean b = false;
		int re = contactMapper.deleteContact(contact_no);
		if(re>0) b = true;
		return b;
	}
}

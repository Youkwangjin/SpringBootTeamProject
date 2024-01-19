package pack.repository.contact;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.dto.contact.ContactDTO;

@Mapper
public interface ContactMapper {
	
	@Select(" select * from contact order by contact_date desc")
	List<ContactDTO> selectContact();
	
	@Select("select count(*) from contact")
	int totalContact();

	@Insert("insert into contact values(coalesce((select max(contact_no)+1 from contact ali), 1),#{contact_name},#{contact_email},#{contact_title},#{contact_message},now(),#{contact_status})")
	int insertContact(ContactDTO contactDTO);

	@Select("select * from contact where contact_no=#{contact_no} order by contact_date desc")
    ContactDTO detailContact(String contact_no);
	
	@Update("update contact set contact_status=#{contact_status} where contact_no=#{contact_no}")
	int updateContact(ContactDTO contactDTO);
	
	@Delete("delete from contact where contact_no=#{contact_no}")
	int deleteContact(String contact_no);
}

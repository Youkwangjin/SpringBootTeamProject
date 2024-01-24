package pack.mapper.fqa;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pack.dto.faq.FaqDTO;


@Mapper
public interface FaqMapper {
	@Select("select * from faq order by faq_no desc")
	List<FaqDTO> selectFaq();
	
	@Select("select * from faq where ${searchName} like concat('%',#{searchValue},'%') order by faq_no desc")
	List<FaqDTO> searchFaq(FaqDTO faqDTO);
	
	@Select("select count(*) from faq ")
	int totalFaq();
	
	@Insert("insert into faq values((select max(faq_no)+1 from faq ali),#{faq_category},#{faq_question},#{faq_answer})")
	int insertFaq(FaqDTO faqDTO);
	
	@Update("update faq set faq_category=#{faq_category},faq_question=#{faq_question},faq_answer=#{faq_answer} where faq_no=#{faq_no}")
	int updateFaq(FaqDTO faqDTO);
	
	@Delete("delete from faq where faq_no=#{faq_no}")
	int deleteFaq(String faq_no);
	
	@Select("select * from faq where faq_no=#{faq_no}")
    FaqDTO selectNo(int faq_no);
}

package pack.mapper.admin;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pack.dto.form.FormDTO;
import pack.dto.owner.OwnerDTO;

import java.util.List;

@Mapper
public interface OwnerInfoListMapper {

   @Select("select * from owner")
   List<OwnerDTO> selectOwner();
   
   @Select("SELECT * FROM owner WHERE business_num LIKE CONCAT('%', #{searchValue}, '%')")
   List<OwnerDTO> selectOwnerSearch(FormDTO bean);

   @Select("select count(*) from owner")
   int ownerRecords();
   
   @Delete("delete from owner where business_num=#{business_num}")
   int ownerDeleteData(String business_num);
}

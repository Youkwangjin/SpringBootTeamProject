package pack.repository.data;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pack.dto.form.FormDTO;
import pack.dto.container.ContainerDTO;
import pack.dto.owner.OwnerDTO;
import pack.dto.user.UserDTO;

@Mapper
public interface DataMapper {
   @Select("select * from user")
   List<UserDTO> selectUser();

   @Select("SELECT * FROM user WHERE user_name LIKE CONCAT('%', #{searchValue}, '%') OR user_id LIKE CONCAT('%', #{searchValue}, '%')")
   List<UserDTO> selectUserSearch(FormDTO bean);

   @Select("select count(*) from user ")
   int totalUser();
   
   @Delete("delete from user where user_id=#{user_id}")
   int userDeleteData(String user_id);
   
   @Select("SELECT COUNT(*) FROM user")
   int userCount();

   @Select("select * from owner")
   List<OwnerDTO> selectOwner();
   
   @Select("SELECT * FROM owner WHERE business_num LIKE CONCAT('%', #{searchValue}, '%')")
   List<OwnerDTO> selectOwnerSearch(FormDTO bean);

   @Select("select count(*) from owner")
   int ownerRecords();
   
   @Delete("delete from owner where business_num=#{business_num}")
   int ownerDeleteData(String business_num);

   @Select("select * from container")
   List<ContainerDTO> selectContainer();
   
   @Select("select count(*) from container")
   int totalContainer();
   
   @Select("SELECT * FROM container WHERE cont_name LIKE CONCAT('%', #{searchValue}, '%')")
   List<ContainerDTO> selectContainerSearch(FormDTO formDTO);

   @Select("select * from container where cont_no=#{cont_no}")
   ContainerDTO selectOne(String cont_no);

   @Delete("delete from container where cont_no=#{cont_no}")
   int delete(String cont_no);

   @Select("select * from container")
   List<ContainerDTO> selectMyContainer();
}

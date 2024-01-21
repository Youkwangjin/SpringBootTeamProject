package pack.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pack.dto.form.FormDTO;
import pack.dto.user.UserDTO;

@Mapper
public interface UserInfoListMapper {
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
}

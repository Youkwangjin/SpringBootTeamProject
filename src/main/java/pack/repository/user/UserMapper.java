package pack.repository.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import pack.dto.user.UserDTO;
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (user_id, user_pwd, user_name, user_tel, user_email, user_addr, user_jumin) \r\n"
            + "VALUES (#{user_id}, #{user_pwd}, #{user_name}, #{user_tel}, #{user_email}, #{user_addr}, #{user_jumin})")
    int userInsertData(UserDTO userDto);


    @Select("select * from user where user_id=#{user_id} and user_pwd=#{user_pwd}")
    UserDTO userLoginProcess(@Param("user_id") String user_id, @Param("user_pwd") String user_pwd);

    @Update("update user set user_pwd=#{user_pwd}, user_name=#{user_name}, user_tel=#{user_tel}, user_email=#{user_email}, user_addr=#{user_addr}, user_jumin=#{user_jumin} where user_id=#{user_id}")
    int userUpdate(UserDTO userDto);

    @Delete("delete from user WHERE user_id = #{user_id}")
    int userDelete(UserDTO userDto);

    @Select("select count(*) from user where user_id=#{user_id}")
    int userIdCheck(@Param("user_id") String user_id);

    @Select("select user_id from user where user_name = #{user_name} and user_email = #{user_email} and user_jumin = #{user_jumin}")
    UserDTO userIdFind(@Param("user_name") String user_name, @Param("user_email") String user_email, @Param("user_jumin") String user_jumin);



}

package pack.repository.admin;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.dto.admin.AdminDTO;
@Mapper
public interface AdminMapper {
    @Select("select * from admin where admin_id=#{admin_id} and admin_pwd=#{admin_pwd}")
    AdminDTO adminLoginProcess(@Param("admin_id") String admin_id, @Param("admin_pwd") String admin_pwd);
}

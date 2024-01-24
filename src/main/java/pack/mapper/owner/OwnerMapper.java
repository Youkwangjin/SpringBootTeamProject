package pack.mapper.owner;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.dto.owner.OwnerDTO;

@Mapper
public interface OwnerMapper {

    @Insert("INSERT INTO owner (business_num, owner_pwd, owner_name, owner_tel, email) \r\n"
            + "VALUES (#{business_num}, #{owner_pwd}, #{owner_name}, #{owner_tel}, #{email})")
    int ownerInsertData(OwnerDTO ownerDto);

    @Select("select * from owner where business_num=#{business_num} and owner_pwd=#{owner_pwd}")
    OwnerDTO ownerLoginProcess(@Param("business_num") String business_num, @Param("owner_pwd") String owner_pwd);

    @Update("update owner set owner_pwd=#{owner_pwd}, owner_name=#{owner_name}, owner_tel=#{owner_tel}, email=#{email} where business_num=#{business_num}")
    int ownerUpdate(OwnerDTO ownerDto);

    @Delete("delete from owner WHERE business_num = #{business_num}")
    int ownerDelete(OwnerDTO ownerDto);

    @Select("SELECT COUNT(*) FROM owner WHERE business_num = #{business_num}")
    int checkBusinessNum(@Param("business_num") String businessNum);

}

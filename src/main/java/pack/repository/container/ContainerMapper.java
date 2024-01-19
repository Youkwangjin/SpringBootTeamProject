package pack.repository.container;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.dto.booking.bookingDTO;
import pack.dto.container.ContainerDTO;


@Mapper
public interface ContainerMapper {
	
	@Select("select * from container inner join owner on owner.business_num=container.owner_num where business_num = #{business_num}")
	List<ContainerDTO> selectAll(@Param("business_num") String business_num);

	@Insert("""
        insert into container(cont_addr, cont_we, cont_kyung, cont_size, cont_image, owner_num, cont_explain, cont_name, owner_phone)
        values (#{cont_addr}, #{cont_we}, #{cont_kyung}, #{cont_size}, #{cont_image}, #{owner_num}, #{cont_explain}, #{cont_name}, #{owner_phone})
        """)
	int insertContainer(ContainerDTO containerDTO);

	@Select("SELECT MAX(cont_no) FROM container")
	Integer getMaxContNo();
	
	@Select("SELECT * FROM container INNER JOIN owner ON owner.business_num = container.owner_num WHERE cont_no = #{cont_no}")
	ContainerDTO selectOne(String cont_no);
	
	@Select("SELECT * FROM container inner join booking_board on container.cont_no = booking_board.cont_no inner join user on user.user_id = booking_board.user_id where container.cont_no = #{cont_no}")
	bookingDTO selectBookCont(String cont_no);
	
	@Update("update container inner join owner on owner.business_num=container.owner_num set owner_name=#{owner_name}, cont_addr=#{cont_addr}, cont_we=#{cont_we}, cont_kyung=#{cont_kyung}, cont_size=#{cont_size}, cont_explain=#{cont_explain} where cont_no=#{cont_no}")
	int updateContainer(ContainerDTO containerDTO);
	
	@Delete("delete from container where cont_no = #{cont_no}")
	int deleteContainer(String cont_no);
	
	@Select("select * from container inner join owner on owner.business_num = container.owner_num where cont_status=2 and business_num = #{business_num}")
	List<ContainerDTO> selectReserve(@Param("business_num") String business_num);
}

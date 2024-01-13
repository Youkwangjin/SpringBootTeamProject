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
   // User sql문들
   @Select("select * from user")
   List<UserDTO> selectAll();

   @Select("SELECT * FROM user WHERE user_name LIKE CONCAT('%', #{searchValue}, '%') OR user_id LIKE CONCAT('%', #{searchValue}, '%')")
   List<UserDTO> selectSearch(FormDTO bean);


   @Select("select count(*) from user ")
   int totalUser();
   
   @Delete("delete from user where user_id=#{user_id}")
   int userdeleteData(String user_id);
   
   @Select("SELECT COUNT(*) FROM user")  // 사용자 테이블에 대한 SQL 쿼리
   int usercount();
   
   // owner sql문들
   @Select("select * from owner")
   List<OwnerDTO> selectAll2();
   
   @Select("SELECT * FROM owner WHERE business_num LIKE CONCAT('%', #{searchValue}, '%')")
   List<OwnerDTO> selectSearch2(FormDTO bean);

   @Select("select count(*) from owner")
   int ownerrecords();
   
   @Delete("delete from owner where business_num=#{business_num}")
   int ownerdeleteData(String business_num);
   
   // Container sql문들
   @Select("select * from container")
   List<ContainerDTO> selectAll3();
   
   @Select("select count(*) from container")
   int totalContainer();
   
   @Select("SELECT * FROM container WHERE cont_name LIKE CONCAT('%', #{searchValue}, '%')")
   List<ContainerDTO> selectSearch3(FormDTO bean);
   
   @Select("select * from container where cont_no=#{cont_no}")  // 세부정보 보기
   ContainerDTO selectOne(String cont_no);
   
   
   @Delete("delete from container where cont_no=#{cont_no}")   // 삭제하기
   int delete(String cont_no);
   
   // 마이페이지에서 자신이 사용한 리뷰/평가 누르면 창고의 번호, 주소, 사진 출력
   @Select("select * from container")
   List<ContainerDTO> selectAll4();
}

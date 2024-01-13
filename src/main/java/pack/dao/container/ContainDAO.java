package pack.dao.container;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.booking.bookingDTO;
import pack.dto.container.ContainerDTO;
import pack.dto.container.ContainMapDTO;
import pack.repository.container.ContainerMapper;
import pack.repository.kakao.KakaoMapper;

//재민
@Repository
public class ContainDAO {
   @Autowired
   private KakaoMapper kakaoMapper; // 히카리

   // 전체자료 읽기
   public List<ContainMapDTO> getcontainAll() {
      List<ContainMapDTO> slist = kakaoMapper.MapAll();
      return slist;
   }

   private Logger logger = LoggerFactory.getLogger(this.getClass());

   @Value("${C:/Users/cmh17/git/Team/src/main/resources/static/upload}") // 추후 경로 수정 필요!
   private String uploadDirectory;

   @Autowired
   private ContainerMapper containerMapper;

   public List<ContainerDTO> getDataAll(String business_num) { // 창고 전체보기
      List<ContainerDTO> list = containerMapper.selectAll(business_num);
      return list;
   }
   
   public List<ContainerDTO> getDataReserve(String business_num) { // 예약된 창고 정보 보기
      List<ContainerDTO> list = containerMapper.selectReserve(business_num);
      return list;
   }

   public ContainerDTO conDetail(String cont_no) { // 상세보기 용
      System.out.println("cont_no : " + cont_no);
      ContainerDTO conDto = containerMapper.selectOne(cont_no);
      System.out.println(conDto);
      return conDto;
   }
   
   public bookingDTO selectbookcont(String cont_no) {
	   bookingDTO bookDto = containerMapper.selectbookcont(cont_no);
	   System.out.println(bookDto);
	   return bookDto;
   }
   

   @Transactional
   public boolean insertContainer(ContainerDTO containerDTO) {
      boolean b = false;
      int re = containerMapper.insertContainer(containerDTO);
      if (re > 0)
         b = true;
      return b;
   }

   @Transactional // 성공하면 커밋 실패하면 롤백
   public boolean update(ContainerDTO containerDTO) {
      boolean b = false;
      int re = containerMapper.update(containerDTO);
      if (re > 0)
         b = true;
      return b;
   }

   @Transactional // 성공하면 커밋 실패하면 롤백
   public boolean delete(String cont_no) {
      boolean b = false;
      int re = containerMapper.delete(cont_no);
      if (re > 0)
         b = true;
      return b;
   }
}
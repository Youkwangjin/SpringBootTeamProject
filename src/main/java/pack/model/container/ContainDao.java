package pack.model.container;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.controller.container.FormBean;
import pack.model.booking.bookingDTO;

//재민
@Repository
public class ContainDao {
   @Autowired
   private kakaoMapperInterface kakaoMapperInterface; // 히카리

   // 전체자료 읽기
   public List<ContainDtoMap> getcontainAll() {
      List<ContainDtoMap> slist = kakaoMapperInterface.MapAll();

      return slist;
   }

   // 민혁

   private Logger logger = LoggerFactory.getLogger(this.getClass());

   @Value("${C:/Users/cmh17/git/Team/src/main/resources/static/upload}")
   private String uploadDirectory;

   @Autowired
   private ContainerMapperInterface containerMapper;
   // HikariCP는 스프링부트에 기본으로 내장되어 있는 JDBC 데이터베이스 커넥션 풀링 프레임워크이다.

   public List<ContainerDto> getDataAll(String business_num) { // 창고 전체보기
	   // 컨트롤러로부터 요청받은 dto멤버들의 값을 전달하기 위한 메소드
      List<ContainerDto> list = containerMapper.selectAll(business_num);
      // dao에서는 다시 mapperinterface에게 값을 요청함
      return list;
      // 요청받은 값들은 list에 리턴해줌.

   }
   
   public List<ContainerDto> getDataReserve(String business_num) { // 예약된 창고 정보 보기
      List<ContainerDto> list = containerMapper.selectReserve(business_num);
      return list;
   }

   public ContainerDto conDetail(String cont_no) { // 상세보기 용
      System.out.println("cont_no : " + cont_no);
      ContainerDto conDto = containerMapper.selectOne(cont_no);
      System.out.println(conDto);
      return conDto;
   }
   
   public bookingDTO  selectbookcont(String cont_no) {
	   bookingDTO bookDto = containerMapper.selectbookcont(cont_no);
	   System.out.println(bookDto);
	   return bookDto;
   }
   

   @Transactional
   public boolean insertContainer(FormBean bean) {
      // 요거슨 매퍼를 통해 bean값을 전달받아 데이터를 블라블라해서
      // 리턴값을 컨트롤러로 전달하는 거같음
      // 컨트롤러로 이동해봐 insertSubmit() 메소드로
      boolean b = false;
      int re = containerMapper.insertContainer(bean);
      if (re > 0)
         b = true;
      return b;
   }

//   @Transactional
//   public void insertContainer(FormBean bean) {
//        containerMapper.insertContainer(bean);
//    }
//
//    public String uploadImage(MultipartFile file) throws IOException {
//        String originalFileName = file.getOriginalFilename();
//        String cont_image = UUID.randomUUID().toString() + "_" + originalFileName;
//
//        // 파일 저장 경로 설정 (uploadDirectory는 application.properties에서 설정)
//        String uploadDirectory = "uploads";
//        String filePath = uploadDirectory + File.separator + cont_image;
//
//        // 디렉토리 생성 (없는 경우)
//        File directory = new File(uploadDirectory);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        // 이미지 파일을 서버에 저장
//        file.transferTo(new File(filePath));
//
//        return cont_image; // 업로드된 파일명 반환
//    }

   @Transactional // 성공하면 커밋 실패하면 롤백
   public boolean update(FormBean bean) {
      boolean b = false;
      int re = containerMapper.update(bean);
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
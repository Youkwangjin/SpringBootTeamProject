package pack.service.container.containerimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import pack.dao.container.ContainDAO;
import pack.dto.container.ContainerDTO;
import pack.dto.upload.UploadFileDTO;
import pack.service.container.ContainerService;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@AllArgsConstructor
public class ContainerServiceImpl implements ContainerService {
    private final ContainDAO containDao;

    @Override
    public Map<String, Object> containerProcess() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> data;

        for (ContainerDTO c : containDao.getContainerAll()) {
            data = new HashMap<>();
            data.put("cont_no", c.getCont_no());
            data.put("cont_name", c.getCont_name());
            data.put("cont_addr", c.getCont_addr());
            data.put("we", String.valueOf(c.getCont_we()));
            data.put("kyung", String.valueOf(c.getCont_kyung()));
            data.put("cont_size", c.getCont_size());
            data.put("cont_status", c.getCont_status());
            list.add(data);
        }
        System.out.println(list);

        Map<String, Object> sangList = new HashMap<>();
        sangList.put("datas", list);
        return sangList;
    }

    @Override
    public List<ContainerDTO> getContainerAllData(String business_num) {
        return containDao.getContainerAllData(business_num);
    }

    @Override
    public List<ContainerDTO> getDataReserve(String business_num) {
        return containDao.getDataReserve(business_num);
    }

    @Transactional
    @Override
    public boolean updateContainer(ContainerDTO containerDTO) {
        return containDao.updateContainer(containerDTO);
    }

    @Transactional
    @Override
    public boolean deleteContainer(String cont_no) {
        return containDao.deleteContainer(cont_no);
    }

    @Override
    public ContainerDTO containerDetail(String cont_no) {
        return containDao.containerDetail(cont_no);
    }

    @Override
    public ResponseEntity<?> processReservation(String cont_no) {
        ContainerDTO container = this.containerDetail(cont_no);

        if (container != null && container.getCont_status().equals("1")) {
            // 예약 로직 수행
            return ResponseEntity.ok("예약 페이지로 이동 합니다.");
        } else if (container != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자의 승인이 필요한 창고입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당 창고를 찾을 수 없습니다.");
        }
    }



    @Override
    public double[] getCoordinatesFromAddress(String address) {
        double[] coordinates = new double[2];
        try {
            String apiKey = "AIzaSyDzGKmDfbyNTWo-0WqNSdQlQSlxc6Wjna4";
            String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // JSON 응답에서 위도와 경도 추출
            ObjectMapper objectMapper = new ObjectMapper();
            Map<?, ?> data = objectMapper.readValue(response.toString(), Map.class);
            if (data.containsKey("results")) {
                List<?> results = (List<?>) data.get("results");
                if (!results.isEmpty()) {
                    Map<?, ?> result = (Map<?, ?>) results.get(0);
                    if (result.containsKey("geometry")) {
                        Map<?, ?> geometry = (Map<?, ?>) result.get("geometry");
                        if (geometry.containsKey("location")) {
                            Map<?, ?> location = (Map<?, ?>) geometry.get("location");
                            coordinates[0] = (Double) location.get("lat");
                            coordinates[1] = (Double) location.get("lng");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinates;
    }

    @Override
    public boolean isAllowedExtension(String filename) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png"};
        for (String extension : allowedExtensions) {
            if (filename.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public String insertContainerWithFile(ContainerDTO containerDTO, UploadFileDTO uploadFileDTO, BindingResult result, String uploadDirectory) {
        if (result.hasErrors()) {
            return "container/container-error";
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            MultipartFile file = uploadFileDTO.getFile();
            String originalFilename = file.getOriginalFilename();
            String randomFilename = UUID.randomUUID().toString();

            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            if (originalFilename != null && !originalFilename.isEmpty()) {
                randomFilename = originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + randomFilename + fileExtension;
            } else {
                randomFilename += fileExtension;
            }

            String fileSavePath = uploadDirectory + randomFilename;

            File directory = new File(fileSavePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs(); // 상위 디렉터리 생성
            }

            File newFile = new File(fileSavePath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            containerDTO.setCont_image(randomFilename);

            boolean isInserted = containDao.insertContainer(containerDTO);
            if (!isInserted) {
                throw new Exception("Database insert 실패!");
            }
        } catch (Exception e) {
            return "container/container-error";
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/owner/list";
    }

}

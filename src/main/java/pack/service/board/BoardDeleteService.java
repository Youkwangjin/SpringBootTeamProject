package pack.service.board;


import org.springframework.stereotype.Service;

@Service
public interface BoardDeleteService {
    boolean delete(String num);
}


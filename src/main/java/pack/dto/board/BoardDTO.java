package pack.dto.board;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private int num, readcnt, gnum, onum, nested;
    private String admin_id, title, cont, bdate, ip;
    private String searchName, searchValue;

    public void setBdate() {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        this.bdate = year + "-" + month + "-" + day;
    }
}

package pack.dto.board;

import lombok.Data;

@Data
public class BoardDTO {
	private int num, readcnt, gnum, onum, nested;
	private String admin_id, title, cont, bdate;
}

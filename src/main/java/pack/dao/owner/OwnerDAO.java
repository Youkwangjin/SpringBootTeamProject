package pack.dao.owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import pack.dto.owner.OwnerDTO;
import pack.mapper.owner.OwnerMapper;


@Repository
@AllArgsConstructor
public class OwnerDAO {
	

	private final OwnerMapper ownerMapper;
	
	
	private boolean isEmpty(String value) {
	    return value != null && !value.trim().isEmpty();
	}

	private boolean joinOwnerData(OwnerDTO ownerDto) {
	    boolean b = true;

        b = isEmpty(ownerDto.getBusiness_num()) &&
				isEmpty(ownerDto.getOwner_pwd()) &&
				isEmpty(ownerDto.getOwner_name()) &&
				isEmpty(ownerDto.getOwner_tel()) &&
				isEmpty(ownerDto.getEmail()) &&
                // 정규식 표현 적용
                ownerDto.getBusiness_num().matches("^[0-9-]+$") &&
                ownerDto.getOwner_tel().matches("^[0-9-]+$") &&
                ownerDto.getOwner_pwd().equals(ownerDto.getOwner_repwd()) &&
                ownerDto.getOwner_name().matches("^[가-힣]{2,}$") &&
                ownerDto.getOwner_pwd().matches("^.{4,}$") &&
                ownerDto.getEmail().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");

	    return b;
	}

	public boolean ownerInsertData(OwnerDTO ownerDto) {
	    boolean b = false;
	    if (joinOwnerData(ownerDto)) {
	        int re = ownerMapper.ownerInsertData(ownerDto);
	        if (re > 0) {
	            b = true;
	        }
	    }
	    return b;
	}

    public OwnerDTO ownerLoginProcess(String business_num, String owner_pwd) {
        return ownerMapper.ownerLoginProcess(business_num, owner_pwd);
    }

    public boolean ownerUpdate(OwnerDTO ownerDto) {
    	boolean b = false;
    	int re = ownerMapper.ownerUpdate(ownerDto);
		if(re > 0) b = true;
		return b;
    }

    public boolean ownerDelete(OwnerDTO ownerDto) {
    	boolean b = false;
    	int re = ownerMapper.ownerDelete(ownerDto);
		if(re >= 0) b = true;
		return b;
    }
}

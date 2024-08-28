package pack.service.owner;

import org.springframework.stereotype.Service;
import pack.model.owner.Owner;

@Service
public interface OwnerService {

    boolean isOwnerEmailDuplicate(String ownerEmail);

    boolean isBusinessNumDuplicate(String ownerBusinessNum);

    boolean isTelPhoneDuplicate(String ownerTel);

    void ownerRegister(Owner owner);
}

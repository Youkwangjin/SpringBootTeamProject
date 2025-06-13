package com.acorn.api.dto.contact.request;

import com.acorn.api.dto.common.CommonListReqDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactListReqDTO extends CommonListReqDTO {

    private Integer contactUserId;

    private Integer contactOwnerId;
}
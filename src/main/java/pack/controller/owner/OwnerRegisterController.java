package pack.controller.owner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.api.code.common.ApiHttpSuccessCode;
import pack.api.response.ApiSuccessResponse;
import pack.model.owner.Owner;
import pack.service.owner.OwnerService;


@RestController
@RequiredArgsConstructor
public class OwnerRegisterController {

    private static final Logger log = LoggerFactory.getLogger(OwnerRegisterController.class);
    private final OwnerService ownerService;

    @PostMapping("/api/auth/owner/register")
    public ResponseEntity<ApiSuccessResponse<Object>> registerOwner(@Valid @RequestBody Owner owner) {

        log.info(" *****************************    Register START    *****************************");

        ownerService.ownerRegister(owner);

        ApiSuccessResponse<Object> registerResponse = ApiSuccessResponse.builder()
                .resultCode(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getStatus())
                .resultMsg(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getStatus()).body(registerResponse);
    }
}

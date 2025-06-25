package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.request.AdminFaqRegisterReqDTO;
import com.acorn.api.dto.admin.request.AdminFaqUpdateReqDTO;
import com.acorn.api.dto.admin.response.AdminFaqDetailResDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminFaqService {
    AdminFaqDetailResDTO getFaqDetailData(Integer faqId);

    void faqRegister(AdminFaqRegisterReqDTO registerData);

    void faqUpdate(AdminFaqUpdateReqDTO updateData);
}
package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.response.AdminFaqDetailResDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminFaqService {
    AdminFaqDetailResDTO getFaqDetailData(Integer faqId);
}
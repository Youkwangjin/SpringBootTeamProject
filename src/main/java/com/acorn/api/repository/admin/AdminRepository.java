package com.acorn.api.repository.admin;

import com.acorn.api.entity.admin.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminRepository {

    Admin findByAdminEmail(@Param("adminEmail") String adminEmail);

    Admin selectAdminById(@Param("adminId") Integer adminId);
}
package com.github.liuweijw.business.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.wechat.domain.AuthInfo;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

}

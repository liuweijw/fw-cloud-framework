package com.github.liuweijw.business.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.wechat.domain.WechatInfo;

public interface WechatInfoRepository extends JpaRepository<WechatInfo, Long> {

}

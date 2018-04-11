package com.github.liuweijw.business.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.pay.domain.PayMchNotify;

public interface MchNotifyRepository extends JpaRepository<PayMchNotify, Long> {

}

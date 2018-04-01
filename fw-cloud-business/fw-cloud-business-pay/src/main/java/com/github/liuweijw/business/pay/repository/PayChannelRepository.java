package com.github.liuweijw.business.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.pay.domain.PayChannel;

public interface PayChannelRepository extends JpaRepository<PayChannel, Long> {

}

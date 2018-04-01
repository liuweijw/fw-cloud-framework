package com.github.liuweijw.business.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.pay.domain.RefundOrder;

public interface RefundOrderRepository extends JpaRepository<RefundOrder, Long> {

}

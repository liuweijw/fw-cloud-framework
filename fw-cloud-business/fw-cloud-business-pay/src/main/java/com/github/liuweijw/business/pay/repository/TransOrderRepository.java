package com.github.liuweijw.business.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.pay.domain.TransOrder;

public interface TransOrderRepository extends JpaRepository<TransOrder, Long> {

}

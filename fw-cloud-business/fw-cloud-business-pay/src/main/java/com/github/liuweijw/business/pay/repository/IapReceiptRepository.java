package com.github.liuweijw.business.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.liuweijw.business.pay.domain.IapReceipt;

public interface IapReceiptRepository extends JpaRepository<IapReceipt, Long> {

}

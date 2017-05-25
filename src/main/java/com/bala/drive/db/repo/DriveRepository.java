package com.bala.drive.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bala.drive.db.bean.DriveListBean;

@Repository
public interface DriveRepository extends MongoRepository<DriveListBean, String>{

	public DriveListBean findByCouponCode(String code);
}

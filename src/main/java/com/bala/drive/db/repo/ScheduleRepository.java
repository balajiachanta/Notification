package com.bala.drive.db.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bala.drive.db.bean.ChooseSchedule;

@Repository
public interface ScheduleRepository extends MongoRepository<ChooseSchedule, String>{

	
	List<ChooseSchedule> findByStatus(ChooseSchedule.schedule status);
}

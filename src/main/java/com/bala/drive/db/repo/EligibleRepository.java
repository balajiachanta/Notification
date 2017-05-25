package com.bala.drive.db.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bala.drive.db.bean.EligibilityFile;

@Repository
public interface EligibleRepository extends MongoRepository<EligibilityFile, String>{


	public List<EligibilityFile> findById(int id);
}

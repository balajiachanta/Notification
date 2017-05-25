package com.bala.drive.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bala.drive.db.bean.ChooseSchedule;
import com.bala.drive.db.bean.DriveListBean;
import com.bala.drive.db.bean.EligibilityFile;
import com.bala.drive.db.repo.DriveRepository;
import com.bala.drive.db.repo.EligibleRepository;
import com.bala.drive.db.repo.ScheduleRepository;

@Component
public class DriveDAO {

	@Autowired
	private DriveRepository driveRepository;

	@Autowired
	private EligibleRepository eligibleRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;


	public void insertDrive(DriveListBean drive){
		//driveRepository.deleteAll();
		driveRepository.insert(drive);
	}

	public void insertEligibleFile(EligibilityFile file){

		//eligibleRepository.deleteAll();
		eligibleRepository.insert(file);
	}

	public void insertDriveSchedule(ChooseSchedule schedule){

		//scheduleRepository.deleteAll();
		scheduleRepository.insert(schedule);
	}

	public List<EligibilityFile> retrieveEligibleAll(){
		List<EligibilityFile> eligibilityList = null;
		eligibilityList = eligibleRepository.findAll();
		if(eligibilityList == null ){
			eligibilityList = new ArrayList<>();
		}
		return eligibilityList;

	}

	public List<DriveListBean> retrieveDrivesAll(){
		List<DriveListBean> driveList = null;
		driveList = driveRepository.findAll();
		if(driveList == null ){
			driveList = new ArrayList<>();
		}
		return driveList;		
	}

	public List<ChooseSchedule> retrieveScheduleTasks(){

		List<ChooseSchedule> chooseList = scheduleRepository.findByStatus(ChooseSchedule.schedule.SCHEDULED);
		if(chooseList.size() > 0){
			System.out.println("not emty list");
			/*chooseList.stream().forEach( c -> {c.setStatus(ChooseSchedule.schedule.DONE);
			scheduleRepository.save(c);
			});*/
			
			for(ChooseSchedule c : chooseList){
				c.setStatus(ChooseSchedule.schedule.DONE);
				scheduleRepository.save(c);
			}
		}

		return chooseList;
	}

	public EligibilityFile getEligibilityFile(int id){
		return eligibleRepository.findById(id).get(0);
	}
}

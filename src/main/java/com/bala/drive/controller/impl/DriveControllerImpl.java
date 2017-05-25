package com.bala.drive.controller.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bala.drive.controller.DriveController;
import com.bala.drive.db.DriveDAO;
import com.bala.drive.db.NextSequenceService;
import com.bala.drive.db.Views;
import com.bala.drive.db.bean.ChooseSchedule;
import com.bala.drive.db.bean.DriveListBean;
import com.bala.drive.db.bean.EligibilityFile;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Balaji Achanta
 *
 */

@RestController
public class DriveControllerImpl implements DriveController {

	@Autowired
	private DriveDAO driveDAO;

	@Autowired
	private NextSequenceService nextSequenceService;


	@Override
	public boolean search(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Future trigger(List<String> tokenList) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping(value="/chooseDttm")
	public ChooseSchedule choose(@RequestBody ChooseSchedule schedule) {

		schedule.setId(nextSequenceService.getNextSequence("schedulerSeq"));
		schedule.setStatus(ChooseSchedule.schedule.SCHEDULED);
		driveDAO.insertDriveSchedule(schedule);
		return schedule;
	}

	@JsonView(Views.Response.class)
	@GetMapping(value="/retriveFiles")
	public List<EligibilityFile> retriveFiles() {

		return driveDAO.retrieveEligibleAll();
	}

	@JsonView(Views.Response.class)
	@GetMapping(value="/retriveDrives")
	public List<DriveListBean> retriveDrives() {

		return driveDAO.retrieveDrivesAll();
	}


	@PostMapping(value="/insertDrive",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String insertDrive(@RequestBody DriveListBean drive) throws NoHandlerFoundException{

		boolean isSuccess = false;

		if(drive == null || !"".equals(drive.getId()) ){
			throw new NoHandlerFoundException("404","/insertDrive",null);
		}else{
			isSuccess = true;
			int seq = nextSequenceService.getNextSequence("driveSeq");
			drive.setId(String.valueOf(seq));

			driveDAO.insertDrive(drive);
		}


		return "{\"success\":\""+isSuccess+"\"}";

	}


	@Override
	public List<String> readFile(String filepath, int count) {


		return null;
	}

}

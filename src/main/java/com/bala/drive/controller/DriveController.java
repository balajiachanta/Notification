package com.bala.drive.controller;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.web.servlet.NoHandlerFoundException;

import com.bala.drive.db.bean.ChooseSchedule;
import com.bala.drive.db.bean.DriveListBean;
import com.bala.drive.db.bean.EligibilityFile;



/**
 * @author Balaji Achanta
 *
 */


public interface DriveController {

	public boolean search(String token);
	
	public Future trigger(List<String> tokenList);
	
	public ChooseSchedule choose(ChooseSchedule schedule);
	
	public List<EligibilityFile> retriveFiles();
	
	public String insertDrive(DriveListBean bean) throws NoHandlerFoundException;
	
	public List<String> readFile(String filepath, int count);
}


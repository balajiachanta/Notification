package com.bala.drive.scheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.bala.aws.AWSConfig;
import com.bala.drive.db.DriveDAO;
import com.bala.drive.db.bean.ChooseSchedule;
import com.bala.drive.notification.TriggerComp;

@Configuration
@EnableScheduling
public class DriveScheduler{

	@Autowired
	private DriveDAO driveDAO;
	
	@Autowired
	private TriggerComp triggerComp;
	
	@Autowired
	private AWSConfig awsConfig;
	
	@Value(value="${sqsqueue}")
	private String awsFileNameQueue;
	
	private static final Logger logger = LoggerFactory.getLogger(DriveScheduler.class);

	
	//@Scheduled(fixedDelay = 20000)  //(cron="*/2 * * * * MON-FRI")
	@Transactional
	public void scheduleReadDirectory() throws InterruptedException {
		
		logger.info("Run Job " + LocalDate.now()+ Thread.currentThread().getName());
		List<ChooseSchedule> scheduleList= driveDAO.retrieveScheduleTasks();
		logger.info(" schedule "+scheduleList);
		if(scheduleList.size() > 0){
			
			logger.info("inside schedule");
		
			scheduleList.stream().forEach(
				
				s -> {
					
					this.readFile(driveDAO.getEligibilityFile(s.getFileSeq()).getDirPath(), s.getFileName(),s.getLineCount());
					
				});
		}else{
			logger.info("noting to schedule");
		}
		
	}
	
	@Scheduled(fixedDelay = 20000)
	public void scheduleReadAmazonSQS(){
		logger.info("Schedule Amazon SQS Queue started");
		
		awsConfig.retrieveSqsMessages(awsFileNameQueue);
		
		logger.info("Schedule Amazon SQS Queue end");
	}
	
	
	
	private void readFile(String path,String fileName, int lineCount){
		

       
		List<String> lines = new ArrayList<>();
		
		List<Future<String>> futureList = new ArrayList<>();
		String line = null;

        try {
            
            FileReader fileReader = 
                new FileReader(path+fileName);

           
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	lines.add(line);
            	if(lines.size() == lineCount){
            		List<String> temp = new ArrayList<>();
            		temp.addAll(Collections.unmodifiableList(lines));
            		Future<String> future  =triggerComp.runTask(temp);
            		futureList.add(future);
            		lines.clear();
            	}
            }   

            if(null != lines && lines.size() > 0){
            	Future<String> future  =triggerComp.runTask(lines);
        		futureList.add(future);
        		lines.clear();
            }
            
            bufferedReader.close();    
            futureList.stream().forEach( f -> {
				try {
					logger.info(f.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
            
        }
        catch(FileNotFoundException ex) {
            logger.error(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
        	 logger.error(
                "Error reading file '" 
                + fileName + "'");   
        }
	}
}

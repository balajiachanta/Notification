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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

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
	

	
	@Scheduled(fixedDelay = 20000)  //(cron="*/2 * * * * MON-FRI")
	@Transactional
	public void executeEveryOneMin() throws InterruptedException {
		
		System.out.println("Run Job " + LocalDate.now()+ Thread.currentThread().getName());
		List<ChooseSchedule> scheduleList= driveDAO.retrieveScheduleTasks();
		System.out.println(" schedule "+scheduleList);
		if(scheduleList.size() > 0){
			
			System.out.println("inside schedule");
		
			scheduleList.stream().forEach(
				
				s -> {
					System.out.println("inside");
					this.readFile(driveDAO.getEligibilityFile(s.getFileSeq()).getDirPath(), s.getFileName(),s.getLineCount());
					
				});
		}else{
			System.out.println("noting to schedule");
		}
		
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
					System.out.println(f.get());
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
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}
}

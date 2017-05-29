package com.bala.drive.notification;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Component
public class TriggerComp {

	@Async
	public Future<String> runTask(List<String> list) throws IOException{


		System.out.println(list+Thread.currentThread().getName());

		

		//System.out.println("printing s       "+Thread.currentThread().getName());
		return new AsyncResult<String>(list+Thread.currentThread().getName());
	}
	
	
}

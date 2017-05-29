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

		/*if(null != list || list.size() >0){
			Message message = new Message.Builder().addData("payload", "B64+JSN|eyJjYW1wYWlnbklkIjoiMzY4IiwiZXZlbnROYW1lIjoiUDEwMCIsInRpdGxlIjoiVXBkYXRlIE15IFNwcmludCBNb2JpbGUiLCJhY3Rpb25UeXBlIjoiZXh0ZXJuYWwiLCJhY3Rpb25VcmwiOiJodHRwczovL3BsYXkuZ29vZ2xlLmNvbS9zdG9yZS9hcHBzL2RldGFpbHM/aWQ9Y29tLnNwcmludC5jYXJlIiwiYm9keSI6IkRvd25sb2FkIHRoZSBsYXRlc3QgdmVyc2lvbiBub3cifQ==").build();
			Sender sender = new Sender("AAAARytYZ5k:APA91bF1KIcQNYxrS7BRu1BCLnEWxyTcCXsaSglYmZPxAmbzaW3GaAAyRTYffm3fktAONWTqxfL1Xju365BtHl27raXcPViYi8xxcTJm8b6z5HTwXe16dL5EdPRrHMH8wa1fxFXeyiZf");

			
			list.stream().forEach( l -> 
					{
						
						Result result = null;
						try {
							result = sender.send(message, l, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(result);

					}
					);

			
		}*/

		//System.out.println("printing s       "+Thread.currentThread().getName());
		return new AsyncResult<String>(list+Thread.currentThread().getName());
	}
	
	
}
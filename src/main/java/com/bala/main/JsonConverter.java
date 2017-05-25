package com.bala.main;

import java.io.IOException;

import com.bala.drive.db.bean.ChooseSchedule;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	/*	
		Message message = new Message.Builder().addData("payload", "3432523534252345").build();
		Sender sender = new Sender("AAAARytYZ5k:APA91bF1KIcQNYxrS7BRu1BCLnEWxyTcCXsaSglYmZPxAmbzaW3GaAAyRTYffm3fktAONWTqxfL1Xju365BtHl27raXcPViYi8xxcTJm8b6z5HTwXe16dL5EdPRrHMH8wa1fxFXeyiZf");
		Result result = sender.send(message, "djBKj_E5zqQ:APA91bFPUjasDgF_Ch14qPgtIjh_TTYP4SHmFDwYeRfuA3AoBpd5sUqNWxPbwGdAjs9a944ZDyNkFKZK1hjlm73JA7uSvAcY40C36HApcycIqXENuyKIdDuoKjNekGzfIIh_dKNITypY", 1);
		System.out.println(result);
		
		*/
		ObjectMapper ob = new ObjectMapper();
		
		System.out.println(ob.writeValueAsString(new ChooseSchedule()));
		
		

	}

}

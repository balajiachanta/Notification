package com.bala.aws;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.bala.aws.bean.S3Request;
import com.bala.aws.bean.S3Response;
import com.bala.drive.notification.TriggerComp;

@Configuration
public class AWSConfig {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private AmazonSQS amazonSQS;

	@Autowired
	private TriggerComp triggerComp;

	@Value(value = "${cloud.aws.s3.bucket}")
	private String s3Bucket;



	private static final Logger logger = LoggerFactory.getLogger(AWSConfig.class);


	@EventListener
	public void init(ContextRefreshedEvent event) throws FileNotFoundException{
		logger.debug("testing");
		S3Request s3 = new S3Request();
		s3.setFilePath("/Users/ra908674/Tools/AWS/aws1.txt");

		uploadFileAndNotifySQS(s3);
		//retrieveSqsMessages();
	}


	public String uploadFileAndNotifySQS(S3Request s3Request) throws FileNotFoundException {
		logger.info("uploadImg started");

		File fileToUpload = new File(s3Request.getFilePath());
		InputStream inputStream= new FileInputStream(fileToUpload);

		String key = Instant.now().getEpochSecond() + "_" + fileToUpload.getName();

		logger.debug("the image name "+key);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.addUserMetadata("linesCount", "1");
		metadata.addUserMetadata("driveSeq", "1");
		metadata.addUserMetadata("user", "balu");

		metadata.setLastModified(new Date());
		PutObjectResult bucketResult =amazonS3.putObject(s3Bucket, key, inputStream,metadata);



		S3Response res = new S3Response();
		res.setResult("upload done");
		logger.info("uploadImg end");

		this.setMsgToQueue(key);

		return res.getResult();
	}

	public void setMsgToQueue(String msg){
		logger.info("setMsgToQueue started");
		amazonSQS.sendMessage("MyQueue", msg);

		logger.info("setMsgToQueue end");

	}

	public List<String> retrieveSqsMessages(String queue){
		logger.info("sqsMessages started");

		ReceiveMessageResult mesResult = amazonSQS.receiveMessage(queue);
		List<Message> myQueueList = mesResult.getMessages();
		List<String> mesList = new ArrayList<String>();

		logger.info("Queue URL is "+amazonSQS.getQueueUrl(queue).getQueueUrl());
		if(null != myQueueList && !myQueueList.isEmpty()){

			myQueueList.forEach(
					message -> {
						String fileName = message.getBody();
						mesList.add(fileName);

						try {
							this.readFile(fileName);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}
						logger.info("deleting file name from queue  "+fileName);

						amazonSQS.deleteMessage(amazonSQS.getQueueUrl(queue).getQueueUrl(),message.getReceiptHandle());
					}
					);
		}else{
			logger.info("there are no messages in the queue");
		}
		logger.info("sqsMessages end");
		return mesList;
	}

	private void readFile(String key) throws IOException{
		logger.info("readFile started");
		S3Object s3 =amazonS3.getObject(s3Bucket, key);
		if(null != s3){
			logger.info("Bucket name "+s3.getBucketName());
			s3.getObjectMetadata().getUserMetadata().entrySet().forEach(System.out::println);

			InputStream stream= s3.getObjectContent();

			List<String> result = new ArrayList<>();
			List<Future<String>> futureList = new ArrayList<>();

			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
				buffer.lines().distinct().forEach( s -> {

					result.add(s);

					if(result.size() == Integer.valueOf(s3.getObjectMetadata().getUserMetadata().get("linesCount"))){
						List<String> temp = new ArrayList<>();
						temp.addAll(Collections.unmodifiableList(result));
						Future<String> future = null;
						try {
							future = triggerComp.runTask(temp);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						futureList.add(future);
						result.clear();
					}


				});
			}


		}else{
			logger.error("file is missing in S3, please verify");
		}
		logger.info("readFile end");

	}
}

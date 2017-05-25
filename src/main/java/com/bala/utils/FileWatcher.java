package com.bala.utils;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bala.drive.db.DriveDAO;
import com.bala.drive.db.NextSequenceService;
import com.bala.drive.db.bean.EligibilityFile;

@Service
public class FileWatcher {

	private static final Logger LOG = LoggerFactory.getLogger(FileWatcher.class);

	private WatchService watcher;

	@Value(value = "${dirPath}")
	private String directoryPath;

	private ExecutorService executor;
	
	@Autowired
	private DriveDAO driveDAO;
	
	@Autowired
	private NextSequenceService nextSequenceService;


	@PostConstruct
	public void init() throws IOException {
		executor = Executors.newSingleThreadExecutor();
		watcher = FileSystems.getDefault().newWatchService();
		Path dir = Paths.get(directoryPath);
		dir.register(watcher, new WatchEvent.Kind[]{ENTRY_CREATE,ENTRY_MODIFY});
		this.readDirectory();
	}



	@PreDestroy
	public void cleanup() {
		try {
			watcher.close();
		} catch (IOException e) {
			LOG.error("Error closing watcher service", e);
		}

	}

	private void readDirectory(){


		executor.submit(() -> {
			while(true){

				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}

				key.pollEvents().stream()
				.filter(e -> (e.kind() != OVERFLOW))
				.map(e -> ((WatchEvent<Path>) e).context())
				.forEach(p -> {
					EligibilityFile file = new EligibilityFile();
					int seq = nextSequenceService.getNextSequence("fileWatcher");
					file.setId(seq);
					file.setDirPath(directoryPath);
					file.setName(p.getFileName().toString());
					file.setCreateDttm(LocalDate.now());
					file.setSeq(seq);
					driveDAO.insertEligibleFile(file);

				});

				boolean valid = key.reset(); 
				if (!valid) {
					break;
				}





				/*for (WatchEvent<?> event : key.pollEvents()) {
					System.out.println("test1");
					WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();

					System.out.println(kind.name() + ": " + fileName);

					if (kind == ENTRY_MODIFY ){

					}
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}*/
			}

		});
	}

}

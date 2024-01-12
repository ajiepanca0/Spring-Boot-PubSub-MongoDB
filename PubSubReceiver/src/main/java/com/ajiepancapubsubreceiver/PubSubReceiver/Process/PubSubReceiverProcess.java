package com.ajiepancapubsubreceiver.PubSubReceiver.Process;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ajiepancapubsubreceiver.PubSubReceiver.PubSubDataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
@Component
public class PubSubReceiverProcess {
	
private static final Logger logger = LogManager.getLogger(PubSubReceiverProcess.class);
	
	private Gson gson = new GsonBuilder().create();

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void insertDataToMongo(PubSubDataModel data) {
		logger.info("start insert data karyawan ======= ");
		Document setDocument = new Document();
				
        Date waktu = new Date(data.getWaktu());  

		setDocument.append("nama", data.getNama());
		setDocument.append("alamat", data.getAlamat());
		setDocument.append("umur", data.getUmur());
		setDocument.append("waktu", waktu);

		
		mongoTemplate.getCollection("dataKaryawan").insertOne(setDocument);
		logger.info("Data Karyawan : " + gson.toJson(setDocument));

		
		logger.info("======= end insert data karyawan  ");

	}

}

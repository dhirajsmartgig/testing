package com.smartgigInternal.serviceImp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3bucketStorageService {
  
    @Autowired
    private AmazonS3 s3Client;
    	
    public String uploadFile(MultipartFile file) {

   	    File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      //  s3Client.putObject(new PutObjectRequest("smartgig-internal-project/Documents",fileName,fileObj)); // prod s3
        s3Client.putObject(new PutObjectRequest("smartgig-internal-project3/testingData",fileName,fileObj));   // testing s3
        fileObj.delete();
       // return  "https://smartgig-internal-project.s3.ap-south-1.amazonaws.com/Documents/"+fileName;
       return  "https://smartgig-internal-project3.s3.ap-south-1.amazonaws.com/testingData/"+fileName;
   }
    
    private File convertMultiPartFileToFile(MultipartFile video) {
        File convertedFile = new File(video.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(video.getBytes());
        } catch (IOException e) {
          e.printStackTrace();
        }
        return convertedFile;
    }

    public String uploadCandidateResume(MultipartFile file) {

   	    File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      //  s3Client.putObject(new PutObjectRequest("smartgig-internal-project/candidatesResume",fileName,fileObj));  // prod s3
        s3Client.putObject(new PutObjectRequest("smartgig-internal-project3/testingData",fileName,fileObj));    // testing s3
        fileObj.delete();
//        return  "https://smartgig-internal-project.s3.ap-south-1.amazonaws.com/candidatesResume/"+fileName;   //prod

        return  "https://smartgig-internal-project3.s3.ap-south-1.amazonaws.com/testingData/"+fileName;
        
   }
    
}
    	

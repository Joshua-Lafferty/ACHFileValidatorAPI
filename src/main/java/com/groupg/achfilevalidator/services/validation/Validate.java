package com.groupg.achfilevalidator.services.validation;

import java.io.Reader;

import com.groupg.achfilevalidator.models.ErrorResponse;
import com.groupg.achfilevalidator.models.FileHeader;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

public class Validate {

    public String testValidation(InputStreamSource file) {
    	ErrorResponse errorResponse = new ErrorResponse();
    	try {
            StreamFactory factory = StreamFactory.newInstance();
            Reader reader;

            reader = new InputStreamReader(file.getInputStream());
            factory.load("src/main/resources/ach.xml");
            BeanReader in = factory.createReader("ach", reader);            
            
            FileHeader header;
            while((header = (FileHeader) in.read()) != null){
            	String referenceCode = header.getPriorityCode();
                System.out.println(referenceCode);
                System.out.println(header.getImmediateOriginName());
            }
            errorResponse = new ErrorResponse("clean","NA","File has no errors");
        } catch (IOException e) {
            System.out.println("Reader");
            e.printStackTrace();
            errorResponse = new ErrorResponse("IOError","NA","There has been an error in the backend!!!!");
        } finally {
        	return errorResponse.getType();
        }
    }
}
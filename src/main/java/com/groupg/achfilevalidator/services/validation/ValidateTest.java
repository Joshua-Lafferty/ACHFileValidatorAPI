package com.groupg.achfilevalidator.services.validation;

import java.io.Reader;

import com.groupg.achfilevalidator.models.ACHFile;
import com.groupg.achfilevalidator.models.ErrorResponse;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;

import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service("testValidate")
public class ValidateTest implements Validate{
    
    @Override
    public ErrorResponse convertFile(InputStreamSource file) {
        ErrorResponse error = new ErrorResponse();
    	try {
            
            StreamFactory factory = StreamFactory.newInstance();
            Reader reader;

            reader = new InputStreamReader(file.getInputStream());
            factory.load("src/main/resources/ach.xml");
            BeanReader in = factory.createReader("ach", reader);
            
            ACHFile convertedFile = null;

            while((convertedFile = (ACHFile) in.read()) != null){
            	
            	ValidationTests test = new ValidationTests();
            	//This is a test of the dollar amount error
            	error = test.validFileTotals(convertedFile);
            }
            in.close();
            
            return error;
        } catch (Exception e) {
            System.out.println(e);
        }
        return error;
    }

    @Override
    public ErrorResponse validateFile(ACHFile fileModel) {
        return null;
    }
}
package com.probotisop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/sub/{host}")
	public ResponseEntity<List<String>> show(@PathVariable String host) throws IOException, InterruptedException {
		UUID uuid=UUID.randomUUID(); //Generates random UUID  
		
		String filename = host+uuid;
		
		String command = "python3 sublist3r.py -d "+host+ " -o "+filename+".txt";
						//python3 sublist3r.py -d javatpoint.com -o java.txt && cat java.txt
		
		System.out.println(command);
		
		if(host.contains("http")) {
			
			return new ResponseEntity<List<String>>( HttpStatus.BAD_REQUEST);
		}
		
		else if(host.contains("//")) {
			StringBuffer res = new StringBuffer("Please Enter URL without HTTP or HTTPS");
			return new ResponseEntity<List<String>>( HttpStatus.BAD_REQUEST);
		}
		
		else if(host.contains(":")) {
			StringBuffer res = new StringBuffer("Please Enter URL without HTTP or HTTPS");
			return new ResponseEntity<List<String>>( HttpStatus.BAD_REQUEST);
		}
		
		StringBuffer out = new StringBuffer();
		StringBuffer domains = new StringBuffer();
		List<String> list = new ArrayList<>();
		

		try {
			
			Process process = Runtime.getRuntime().exec(command);
		

		    BufferedReader reader = new BufferedReader(
		            new InputStreamReader(process.getInputStream()));
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		       
		        
		        out.append(line +"\n");
		        
		    }
		    
		    int exitvalue = process.waitFor();
		 
		    reader.close();
		 
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		
		try {
			
			Process process = Runtime.getRuntime().exec("cat "+filename+".txt");
		

		    BufferedReader reader = new BufferedReader(
		            new InputStreamReader(process.getInputStream()));
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		       
		        
		      //  domains.append(line +"\n");
		        list.add(line);
		        
		    }
		    
		    int exitvalue = process.waitFor();
		 
		    reader.close();
		 
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		
		
	}
	
	@GetMapping("/test")
	public String test() {
		return "hey hey boi";
	}
	
	@GetMapping("/")
	public String home() {
		return " go to endpoint   			/sub/{host}				 for subdomain enum";
	}
}

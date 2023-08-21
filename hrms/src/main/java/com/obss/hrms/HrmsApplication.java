package com.obss.hrms;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
@ServletComponentScan
public class HrmsApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(HrmsApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
    }


}

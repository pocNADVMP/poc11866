package com.projectV.ProjectV.controllers;

import com.projectV.ProjectV.model.EmployeeAttendance;
import com.projectV.ProjectV.services.MiddleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@RestController
public class DBController {


    @Autowired
    private MiddleService middleService;
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */


    /*
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */


    @RequestMapping(value = "/attendance/{id}",method = RequestMethod.GET)
    public EmployeeAttendance attendance(@PathVariable String id) throws Exception{




//        UpdateValuesResponse response=write(service,spreadsheetId,range);



        return middleService.getAttendanceOfMonth(id);
    }


    @RequestMapping(value="/updateattendance/{id}/{newAttendance}/{date}",method={RequestMethod.PUT,RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST})
    public UpdateValuesResponse updateAttendance(@PathVariable("id") String id, @PathVariable("newAttendance") String newAttendance, @PathVariable("date") String date) throws Exception{


        return middleService.updateAttendanceOnDate(id,newAttendance,date);

    }




}

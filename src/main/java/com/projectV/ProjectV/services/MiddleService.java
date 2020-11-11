package com.projectV.ProjectV.services;

import com.google.api.services.sheets.v4.model.*;
import com.projectV.ProjectV.controllers.DBController;
import com.projectV.ProjectV.model.EmployeeAttendance;
import org.springframework.stereotype.Service;
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

import java.io.*;
import java.util.*;

@Service
public class MiddleService {

    // This is for the employee who does not have administrator level access.

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static final String spreadsheetId = //enter sheet id here;


    private static EmployeeAttendance employeeAttendance;



    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        // InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        InputStream in = DBController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    private static List<List<Object>> read(Sheets service, String spreadsheetId, String range) throws Exception {
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        return values;
    }
    private static void display(List<List<Object>> values) throws Exception {
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            //System.out.println(values);
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s\n", row.get(3));
            }
        }
    }
    private static UpdateValuesResponse write(Sheets service,String spreadsheetId, String range) throws Exception{
        final String valueInputOption ="USER_ENTERED";
        ValueRange requestBody = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList("welcome")));
        Sheets.Spreadsheets.Values.Update request =
                service.spreadsheets().values().update(spreadsheetId, range, requestBody);
        request.setValueInputOption(valueInputOption);

        UpdateValuesResponse response = request.execute();
        return response;
    }
    private static void DisplayR(List<List<Object>> values) throws Exception {
        System.out.println(values);
    }


    public static EmployeeAttendance getAttendanceOfMonth(String employeeIdentifier) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        final String range = "Sheet1!B"+employeeIdentifier+":AF"+employeeIdentifier;

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<List<Object>> values = read(service,spreadsheetId,range);
        employeeAttendance = new EmployeeAttendance(employeeIdentifier,values.get(0));


        return employeeAttendance;
    }

    public static UpdateValuesResponse updateAttendanceOnDate(String employeeIdentifier, String newAttendance, String date) throws Exception{



        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        final String range = "Sheet1!"+date+employeeIdentifier+":"+date+employeeIdentifier;

        final String valueInputOption ="USER_ENTERED";

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();


        ValueRange requestBody = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(newAttendance)));
        Sheets.Spreadsheets.Values.Update request =
                service.spreadsheets().values().update(spreadsheetId, range, requestBody);
        request.setValueInputOption(valueInputOption);





        UpdateValuesResponse response = request.execute();

        return response;

    }






}

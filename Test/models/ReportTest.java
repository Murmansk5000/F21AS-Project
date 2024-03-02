package models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @org.junit.jupiter.api.Test
    void getReport() {
        Report report = new Report();
        FlightList fl = new FlightList();
        fl.loadFlightsFromTXT("FlightList.txt");
        report.getReport(fl);

    }

}
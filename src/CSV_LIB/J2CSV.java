/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSV_LIB;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author affan
 */
public class J2CSV {
    private String sepratedBy;
    private String CSVpath;
    private ArrayList<Object> obj = new ArrayList<>();

    public J2CSV(String sepratedBy, String CSVpath,ArrayList<Object> obj) {
        this.sepratedBy = sepratedBy;
        this.CSVpath = CSVpath;
        this.obj = obj;
    }

    public void createCSV() throws IOException{
        try (PrintWriter pw = new PrintWriter(new FileWriter(this.getCSVpath()))) {
            
            obj.stream().forEach((dataset) -> {
                    pw.println(dataset.toString());
            });
            pw.flush();
        }
    }   

    public String getSepratedBy() {
        return sepratedBy;
    }

    public void setSepratedBy(String sepratedBy) {
        this.sepratedBy = sepratedBy;
    }

    public String getCSVpath() {
        return CSVpath;
    }

    public void setCSVpath(String CSVpath) {
        this.CSVpath = CSVpath;
    }

    public ArrayList<Object> getObj() {
        return obj;
    }

    public void setObj(ArrayList<Object> obj) {
        this.obj = obj;
    }
    
}

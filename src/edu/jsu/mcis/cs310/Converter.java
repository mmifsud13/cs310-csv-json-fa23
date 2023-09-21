package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
       
        
        try {
        
        CSVReader reader = new CSVReader(new StringReader(csvString));
        List<String[]> full = reader.readAll();
        Iterator<String[]> iterator = full.iterator();
        
        JsonObject jsonObject = new JsonObject();
        JsonArray Column = new JsonArray();
        JsonArray Row = new JsonArray();
        JsonArray Data = new JsonArray();
        JsonArray holder;
        String[] Info = iterator.next();
        
        for(int i=0; i < Info.length; i++)
        {
            Column.add(Info[i]);
        }
        while(iterator.hasNext())
        {
            holder = new JsonArray();
            Info = iterator.next();
            Row.add(Info[0]);
            
            for(int i=1; i < Info.length; i++)
            {
                //int stringHolder = Integer.parseInt(Info[i]);
                holder.add(Info[i]);
            }
            Data.add(holder);
        }
        jsonObject.put("ProdNums", Row);
        jsonObject.put("colHeadings", Column);
        jsonObject.put("Data", Data);
        
        result = jsonObject.toString();
    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        String csvFile = "C:\\Users\\Owner\\OneDrive\\Desktop\\Individual 1 Netbeans\\build\\classes\\resources\\input.csv";
        String line;
        List<String[]> data = new ArrayList<>();
        String[] headers = null;
        BufferedReader br = null;
        
        try 
        {
            br = new BufferedReader(new FileReader(csvFile));

            if ((line = br.readLine()) != null) 
            {
                headers = line.split(",");
            }

            while ((line = br.readLine()) != null) 
            {
                String[] values = line.split(",");
                if (headers != null && values.length == headers.length) 
                {
                    data.add(values);
                }
            }

            for (String[] row : data) 
            {
                for (int i = 0; i < headers.length; i++) 
                {
                    String header = headers[i];
                    String value = row[i];
                    System.out.println(header + ": " + value);
                }
                System.out.println();
            }
            
            Iterator<String[]> iterator = data.iterator();
            
            JsonArray records = new JsonArray();
            
            if (iterator.hasNext()) 
            {
                String[] headings = iterator.next();
                while (iterator.hasNext()) 
                {
                    String[] csvRecord = iterator.next();
                    JsonObject jsonRecord = new JsonObject();
                    for (int i = 0; i < headings.length; ++i) 
                    {
                        jsonRecord.put(headings[i].toLowerCase(), csvRecord[i]);
                    }
                    records.add(jsonRecord);
                }
            }
            Jsoner.serialize(records);
            //result = records.toString();
        }
        
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
}


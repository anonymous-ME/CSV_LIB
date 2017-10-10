/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSV_LIB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Affan Ahmad Fahmi
 */
public class CSV2J {
    
    private String sepratedBy;
    private String CSVpath;
    private String ObjClassName;

    public CSV2J(String sepratedBy, String CSVpath, String ObjClassName) {
        this.sepratedBy = sepratedBy;
        this.CSVpath = CSVpath;
        this.ObjClassName = ObjClassName;
    }
    
    
    public String getObjClassName() {
        return ObjClassName;
    }

    public void setObjClassName(String ObjClassName) {
        this.ObjClassName = ObjClassName;
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


    private String fetchData(String CSVpath) throws IOException{
        String data;
        InputStream in = new FileInputStream(new File(CSVpath));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append("\n");
            }
            data = out.toString();
        }
        return data;
    }
    
    public List<Object> getObj() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception{
        
            String CSVrow[] = fetchData(getCSVpath()).split("\n");
            List<Object> obj= new ArrayList<>();

            for (String CSVrow1 : CSVrow) {

                String[] CSVcolumn = CSVrow1.split(this.getSepratedBy());

                List<String> args = new ArrayList<>();
                args.addAll(Arrays.asList(CSVcolumn));

                obj.add(instantiate(args,this.getObjClassName()));

            }

            return obj;
    }
    
    private static Object convert(Class<?> target, String s) {
        if (target == Object.class || target == String.class || s == null) {
            return s;
        }
        if (target == Character.class || target == char.class) {
            return s.charAt(0);
        }
        if (target == Byte.class || target == byte.class) {
            return Byte.parseByte(s);
        }
        if (target == Short.class || target == short.class) {
            return Short.parseShort(s);
        }
        if (target == Integer.class || target == int.class) {
            return Integer.parseInt(s);
        }
        if (target == Long.class || target == long.class) {
            return Long.parseLong(s);
        }
        if (target == Float.class || target == float.class) {
            return Float.parseFloat(s);
        }
        if (target == Double.class || target == double.class) {
            return Double.parseDouble(s);
        }
        if (target == Boolean.class || target == boolean.class) {
            return Boolean.parseBoolean(s);
        }
        throw new IllegalArgumentException("Don't know how to convert to " + target);
    }
    
    private static Object instantiate(List<String> args, String className) throws Exception {
        // Load the class.
        Class<?> clazz = Class.forName(className);

        // Search for an "appropriate" constructor.
        for (Constructor<?> ctor : clazz.getConstructors()) {
            Class<?>[] paramTypes = ctor.getParameterTypes();

            // If the arity matches, let's use it.
            if (args.size() == paramTypes.length) {

                // Convert the String arguments into the parameters' types.
                Object[] convertedArgs = new Object[args.size()];
                for (int i = 0; i < convertedArgs.length; i++) {
                    convertedArgs[i] = convert(paramTypes[i], args.get(i));
                }

                // Instantiate the object with the converted arguments.
                return ctor.newInstance(convertedArgs);
            }
        }

        throw new IllegalArgumentException("Don't know how to instantiate " + className);
    }
}

package co.com.empresa.certification.demoWeb.utils.datadrive;


import co.com.empresa.certification.demoWeb.exceptions.BackEndExceptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static co.com.empresa.certification.demoWeb.utils.constants.Constants.UNICODE_TRANSFORMATION_FORMAT;
import static co.com.empresa.certification.demoWeb.utils.constants.Numbers.*;

public class DataToFeature {
    protected static boolean foundHashTag = false;
    protected static boolean featureData = false;
    protected static String sheetName;
    protected static String excelFilePath;
    protected static String data;

    private DataToFeature() {
        throw new IllegalStateException("Utility class");
    }


    /**
     *
     * @param featureFile
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    private static List<String> setExcelDataToFeature(File featureFile) throws IOException, InvalidFormatException {
        List<String> fileData = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new BufferedInputStream(
                        new FileInputStream(featureFile)), UNICODE_TRANSFORMATION_FORMAT))
        ){
            foundHashTag = false;
            featureData = false;

            while ((data = bufferedReader.readLine()) != null){
                sheetName = null;
                excelFilePath = null;
                if (data.trim().contains("##@externaldata")){
                    foundHashTag = true;
                    fileData = getDataExcel(fileData);
                }
                if (foundHashTag){
                    foundHashTag = false;
                    featureData = true;
                }else {
                    if ((data.startsWith("|") || data.endsWith("|") && featureData)){
                        continue;
                    }
                    featureData = false;
                    fileData.add(data);
                }
            }

        }
        return fileData;
    }

    /**
     * Lista de todos los features con sus respectivos archivos de excel que se usarán en la prueba
     * @param folder
     * @return featureFiles
     */
    private static List<File> listOfFeatureFiles(File folder){
        List<File> featureFiles = null;
        try{
            featureFiles = new ArrayList<>();
            if (folder.getName().endsWith(".feature")){
                featureFiles.add(folder);
            }else {
                for (File fileEntry : folder.listFiles()){
                    if (fileEntry.isDirectory()){
                        featureFiles.addAll(listOfFeatureFiles(fileEntry));
                    }else {
                        if (fileEntry.isFile() && fileEntry.getName().endsWith(".feature")){
                            featureFiles.add(fileEntry);
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new BackEndExceptions(e);
        }
        return featureFiles;
    }

    /**
     * Hace una lista con todos los features dependiendo de la ruta asignada
     * @param featuresDirectoryPath Ruta donde se encuentra los features que tendrán las tablas
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void overrideFeautreFiles(String featuresDirectoryPath)throws IOException, InvalidFormatException{
        List<File> listOfFeatureFiles = listOfFeatureFiles(new File(featuresDirectoryPath));
        try {
            for (File featuresFile : listOfFeatureFiles){
                List<String> featureWithExcelData = setExcelDataToFeature(featuresFile);
                try(BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(featuresFile), UNICODE_TRANSFORMATION_FORMAT))
                    ;) {
                    for (String string : featureWithExcelData){
                        writer.write(string);
                        writer.write("\n");
                    }
                }
            }
        }catch (InvalidFormatException e){
            throw new BackEndExceptions(e);
        }catch (IOException e2){
            throw new BackEndExceptions(e2);
        }
    }

    /**
     *
     * @param fileData
     * @return fileData
     */
    private static List<String> getDataExcel(List<String> fileData) {
        List<Map<String, String>> excelData = null;

        try{
            excelFilePath = data.substring(StringUtils.ordinalIndexOf(data,"@", INT_2) + INT_1, data.lastIndexOf('@'));
            sheetName = data.substring(data.lastIndexOf('@') + INT_1, data.length());
            fileData.add(data);

            excelData = new ExcelReader().getData(excelFilePath, sheetName);
            for (int rowNumber = INT_0; rowNumber < excelData.size() - INT_1; rowNumber++){
                StringBuilder cellData = new StringBuilder();
                cellData.append("      ");
                for (Map.Entry<String,String> mapData : excelData.get(rowNumber).entrySet()){
                    cellData.append("|" + mapData.getValue());
                }
                cellData.append('|');
                fileData.add(cellData.toString());
            }
        }catch (InvalidFormatException e){
            throw new BackEndExceptions(e);
        }catch (IOException e2){
            throw new BackEndExceptions(e2);
        }
        return fileData;
    }
}

package co.com.empresa.certification.demoWeb.utils.datadrive;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    /**
     * Obtiene los datos de un archivo excel, teniendo en cuenta el nombre de la hoja
     * @param excelFilePath Ruta del libro de excel
     * @param sheetName Nombre de la hoja que contiene los datos
     * @return
     * @throws InvalidFormatException Manejo de error por formato inválido
     * @throws IOException Manejo de error para el proceso de entrada y salida de datos
     */
    public List<Map<String, String>> getData(String excelFilePath, String sheetName) throws InvalidFormatException, IOException{
        Sheet sheet = getSheetByName(excelFilePath, sheetName);
        return readSheet(sheet);
    }

    /**
     * Obtiene la hoja de trabajo donde se encuentra los datos de acuerdo a la ruta del archivo
     * @param excelFilePath Ruta del libro de excel
     * @param sheetNumber Número de la hoja que contiene los datos
     * @return
     * @throws InvalidFormatException Manejo de error por formato inválido
     * @throws IOException Manejo de error para el proceso de entrada y salida de datos
     */
    public List<Map<String, String>> getData(String excelFilePath, int sheetNumber) throws InvalidFormatException, IOException{
        Sheet sheet = getSheetByIndex(excelFilePath, sheetNumber);
        return readSheet(sheet);
    }

    /**
     * Obtiene la hoja de trabajo donde se encuentra los datos de acuerdo a la ruta del archivo
     * @param excelFilePath Ruta del libro de excel
     * @param sheetName Indice de tipo entero de la hoja en el libro de excel
     * @return
     * @throws IOException Manejo de error para el proceso de entrada y salida de datos
     * @throws InvalidFormatException Manejo de error por formato inválido
     */
    private Sheet getSheetByName(String excelFilePath, String sheetName) throws IOException, InvalidFormatException{
        return getWorkBook(excelFilePath).getSheet(sheetName);
    }

    /**
     * Obtiene la hoja de trabajo donde se encuentra los datos de acuerdo al index
     * @param excelFilePath  Ruta del libro de excel
     * @param sheetNumber Indice de tipo entero de la hoka en el libro de excel
     * @return
     * @throws IOException Manejo de error para el proceso de entrada y salida de datos
     * @throws InvalidFormatException Manejo de error por formato inválido
     */
    private Sheet getSheetByIndex(String excelFilePath, int sheetNumber) throws IOException, InvalidFormatException{
        return getWorkBook(excelFilePath).getSheetAt(sheetNumber);
    }

    /**
     * Devuelve el libro correspondiente a la hoja determinada con antelación
     * @param excelFilePath Ruta del archivo excel
     * @return
     * @throws IOException Manejo de error para el proceso de entrada y salida de datos
     * @throws InvalidFormatException Manejo de error por formato inválido
     */
    private Workbook getWorkBook(String excelFilePath) throws IOException, InvalidFormatException{
        return WorkbookFactory.create(new File(excelFilePath));
    }

    /**
     * Retorna la lista en forma de Map de todas las filas que contiene la hoja de excel,
     * teniendo en cuenta la primera fila como los nombres de la columnas
     * @param sheet Hoja de excel
     * @return
     */
    private List<Map<String, String>> readSheet(Sheet sheet){
        Row row;
        int totalRow = sheet.getPhysicalNumberOfRows();
        List<Map<String, String>> excelRows = new ArrayList<>();
        int headerRowNumber = getHeaderRowNumber(sheet);
        if (headerRowNumber != -1){
            int totalColumn = sheet.getRow(headerRowNumber).getLastCellNum();
            int setCurrentRow = 1;
            for (int currentRow = setCurrentRow; currentRow <= totalRow; currentRow++){
                row = getRow(sheet, sheet.getFirstRowNum() + currentRow);
                LinkedHashMap<String, String> columnMapData = new LinkedHashMap<>();
                for (int currentColumn = 0; currentColumn < totalColumn; currentColumn++){
                    columnMapData.putAll(getCellValue(sheet, row, currentColumn));
                }
                excelRows.add(columnMapData);
            }
        }
        return  excelRows;
    }

    /**
     * Obtiene el número de filas concerniente a encabezado de la hoja
     * @param sheet
     * @return
     */
    private int getHeaderRowNumber(Sheet sheet){
        Row row;
        int totalRow = sheet.getLastRowNum();
        for (int currentRow = 0; currentRow <= totalRow +1; currentRow++){
            row = getRow(sheet, currentRow);
            if (row != null){
                int totalColumn = row.getLastCellNum();
                for (int currentColumn = 0; currentColumn < totalColumn; currentColumn++){
                    Cell cell;
                    cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.NUMERIC
                            || cell.getCellType() == CellType.BOOLEAN || cell.getCellType() == CellType.ERROR){
                        return row.getRowNum();
                    }
                }
            }
        }
        return (-1);
    }

    /**
     * Obtiene la fila de acuerdo a la hoja y el número de ésta
     * @param sheet
     * @param rowNumber
     * @return
     */
    private Row getRow(Sheet sheet, int rowNumber){
        return sheet.getRow(rowNumber);
    }
    private LinkedHashMap<String, String> getCellValue(Sheet sheet, Row row, int currentColumn){
        LinkedHashMap<String, String> columnMapData = new LinkedHashMap<>();
        Cell cell;
        if (row == null){
            if (sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                    .getCellType() != CellType.BLANK){
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn).getStringCellValue();
                columnMapData.put(columnHeaderName, "");
            }
        }else {
            cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if(cell.getCellType() == CellType.STRING && sheet.getRow(sheet.getFirstRowNum())
                    .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK){
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                columnMapData.put(columnHeaderName, cell.getStringCellValue());
            }else if (cell.getCellType() == CellType.NUMERIC && sheet.getRow(sheet.getFirstRowNum())
                    .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK){
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                columnMapData.put(columnHeaderName, NumberToTextConverter.toText(cell.getNumericCellValue()));
            } else if (cell.getCellType() == CellType.BLANK && sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                columnMapData.put(columnHeaderName, "");
            } else if (cell.getCellType() == CellType.BOOLEAN && sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                columnMapData.put(columnHeaderName, Boolean.toString(cell.getBooleanCellValue()));
            } else if (cell.getCellType() == CellType.BLANK) {
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                columnMapData.put(columnHeaderName, Byte.toString(cell.getErrorCellValue()));
            }
        }
        return  columnMapData;
    }
}

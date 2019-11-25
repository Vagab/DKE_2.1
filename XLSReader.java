import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSReader {

    File file;
    HSSFWorkbook workbook;
    String currentSheet;


    public XLSReader(String path){
        file = new File(path);
        try{
            setSheet("Sheet1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setWorkbook(){
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSheet(String sheetName) throws IOException {
        setWorkbook();
        currentSheet = sheetName;
        try {
            workbook.getSheet(currentSheet).getRow(0);
        } catch(NullPointerException e) {
            workbook.createSheet(currentSheet);
            workbook.write(new FileOutputStream(file));
            workbook.close();
        }
    }

    public double[] getRowValues(int row) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet(currentSheet);
        HSSFRow thisRow = sheetOne.getRow(row);
        List<Double> valueList = new ArrayList<>();
        for(int i = 0; i<thisRow.getHeight(); i++){
            HSSFCell cell = thisRow.getCell(i);
            if(cell != null){
                valueList.add(cell.getNumericCellValue());
            } else {
                break;
           }
        }
        double[] values = new double[valueList.size()];
        for (int i = 0; i < valueList.size(); ++i) {
            values[i] = valueList.get(i);
        }
        workbook.close();
        return values;
    }

    public double getCellValue(int row, int col) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet(currentSheet);
        HSSFRow thisRow = sheetOne.getRow(row);
        HSSFCell cell = thisRow.getCell(col);
        double value = cell.getNumericCellValue();
        workbook.close();
        return value;
    }



    public void addRowValues(double[] values) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet(currentSheet);
        HSSFRow thisRow = sheetOne.createRow(sheetOne.getLastRowNum()+1);
        for(int i = 0; i < values.length; ++i){
            HSSFCell cell = thisRow.createCell(i);
            cell.setCellValue(values[i]);
        }
        
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    public void changeRowValues(double[] values, int row) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet(currentSheet);
        HSSFRow thisRow = sheetOne.getRow(row);
        for(int i = 0; i<values.length; i++) {
            HSSFCell cell = thisRow.createCell(i);
            cell.setCellValue(values[i]);
        }
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    public void addCellValue(double value, int row, int col) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet(currentSheet);
        HSSFRow thisRow;
        try {
            thisRow = sheetOne.getRow(row);
            thisRow.getFirstCellNum();
        } catch(NullPointerException e) {
            thisRow = sheetOne.createRow(row);
        }
        HSSFCell cell = thisRow.createCell(col);
        cell.setCellValue(value);
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    public void deleteCellValue(int row, int col) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet(currentSheet);
        HSSFRow thisrow = sheetOne.getRow(row);
        thisrow.createCell(col);
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    public static void main(String[] args) {
        try {

            XLSReader reader = new XLSReader("Test.xls");
            double[] testValues = new double[3];
            testValues[0] = 0.0;
            testValues[1] = 21;
            testValues[2] = 2.5;
            reader.addCellValue(1, 2, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
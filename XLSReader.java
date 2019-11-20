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


    public XLSReader(String path){
        file = new File(path);
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

    public double[] getRowValues(int row) throws IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet("Sheet1");
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

    public void addRowValues(double[] values) throws FileNotFoundException, IOException {
        setWorkbook();
        HSSFSheet sheetOne = workbook.getSheet("Sheet1");
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
        HSSFSheet sheetOne = workbook.getSheet("Sheet1");
        HSSFRow thisRow = sheetOne.getRow(row);
        for(int i = 0; i<values.length; i++) {
            HSSFCell cell = thisRow.createCell(i);
            cell.setCellValue(values[i]);
        }
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    public static void main(String[] args){
        XLSReader reader = new XLSReader("Test.xls");
        double[] testValues = new double[3];
        testValues[0] = 0.0;
        testValues[1] = 21;
        testValues[2] = 2.5;
        try {
            //reader.addRowValues(testValues);
            reader.changeRowValues(testValues, 2);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //reader.getRowValues(1);
        //reader.getRowValues(2);
        
        
        //HSSFWorkbook workbook = new HSSFWorkbook();
        //HSSFSheet sheet = workbook.createSheet("First Sheet");
        //HSSFRow row = sheet.createRow(0);
        //HSSFCell cell = row.createCell(0);
        //cell.setCellValue(0.12);
        //workbook.write(new FileOutputStream(new File("Test.xls")));
        //workbook.close();
    }
}
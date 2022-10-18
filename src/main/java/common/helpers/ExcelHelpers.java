package common.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

public class ExcelHelpers {
    private FileInputStream fis;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();
    private Map<String, Integer> rows = new HashMap<>();

    public void setExcelFile(String ExcelPath, String SheetName) throws Exception {
        try {
            File f = new File(ExcelPath);

            if (!f.exists()) {
                f.createNewFile();
                System.out.println("File doesn't exist, so created!");
            }

            fis = new FileInputStream(ExcelPath);
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(SheetName);
            //sh = wb.getSheetAt(0); //0 - index of 1st sheet
            if (sh == null) {
                sh = wb.createSheet(SheetName);
            }

            this.excelFilePath = ExcelPath;

            //adding all the column header names to the map 'columns'
            sh.getRow(0).forEach(cell ->{
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });

            //adding all the row names to the map 'rows'
            for (int i = 1; i <= sh.getLastRowNum(); i++) {
                cell = sh.getRow(i).getCell(0);
                rows.put(cell.getStringCellValue(), cell.getRowIndex());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String[][] getExcelData(){
        try{
            System.out.println("Row:: "+sh.getLastRowNum());
            System.out.println("Column:: "+sh.getRow(0).getLastCellNum());
            Integer row = sh.getLastRowNum();
            Short column = sh.getRow(0).getLastCellNum();
            String[][] data = new String[row][column];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    try {
                        cell = sh.getRow(i).getCell(j);
                        data[i][j] = cell.getStringCellValue();
                    } catch (Exception e) {
                        System.out.println("cell is empty");
                        data[i][j] = null;
                    }


                }
            }

            return data;
        } catch (Exception e){
            System.out.println("Exception: "+e);
            return null;
        }
    }

    public String getCellData(int rownum, int colnum) throws Exception{
        try{
            cell = sh.getRow(rownum).getCell(colnum);
            String CellData = null;
            switch (cell.getCellType()){
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        CellData = String.valueOf(cell.getDateCellValue());
                    }
                    else
                    {
                        CellData = String.valueOf((long)cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        }catch (Exception e){
            return"";
        }
    }

    public String getCellData(String columnName, int rownum) throws Exception {
        return getCellData(rownum, columns.get(columnName));
    }

    public String getCellData(String columnName, String rowName) throws Exception {
        return getCellData(rows.get(rowName), columns.get(columnName));
    }

    public Map<String, String> getTcData(String testcase) {
        Map<String, String> tcData = new HashMap<>();
        sh.getRow(0).forEach(cell ->{
            try {
                tcData.put(cell.getStringCellValue(), getCellData(cell.getStringCellValue(), testcase));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return tcData;
    }

    public Object[][] getParameterFromExcel(String sheet) {
        try{
            System.out.println("The number of cases run for the test:: "+sh.getLastRowNum());
            Integer row = sh.getLastRowNum();
            Object[][] data = new Object[row][];

            for (int i = 1; i <= row; i++) {
                try {
                    cell = sh.getRow(i).getCell(0);
                    data[i-1] = new Object[]{sheet, cell.getStringCellValue()};
                } catch (Exception e) {
                    System.out.println("cell is empty");
                    data[i-1] = null;
                }
            }

            return data;
        } catch (Exception e){
            System.out.println("Exception: "+e);
            return null;
        }
    }

    public void setCellData(String text, int rownum, int colnum) throws Exception {
        try{
            row  = sh.getRow(rownum);
            if(row ==null)
            {
                row = sh.createRow(rownum);
            }
            cell = row.getCell(colnum);

            if (cell == null) {
                cell = row.createCell(colnum);
            }
            cell.setCellValue(text);

            fileOut = new FileOutputStream(excelFilePath);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch(Exception e){
            throw (e);
        }
    }

    public void setCellData(String text, String rowName, String columnName) throws Exception {
        setCellData(text, rows.get(rowName), columns.get(columnName));
    }

}

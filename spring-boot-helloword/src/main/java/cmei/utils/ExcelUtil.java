package cmei.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ExcelUtil
 *
 * @author meicanhua
 * @create 2017-10-23 下午3:30
 **/
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static final String EXCEL_SUFFIX_2007 = ".xlsx";
    public static final String EXCEL_SUFFIX_2003 = ".xls";

    public static Workbook readExcel(String fileName, InputStream inputStream) {
        try {
            if (fileName.toLowerCase().endsWith(EXCEL_SUFFIX_2007)) {
                return new XSSFWorkbook(inputStream);
            } else if (fileName.toLowerCase().endsWith(EXCEL_SUFFIX_2003)) {
                return new HSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Workbook readExcel(String localFile) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFile);
            return readExcel(localFile, inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public static String convertCellValue(Cell cell) {
        CellType cellType  = cell.getCellTypeEnum();
        switch (cellType) {
            case BLANK:
                return "";
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }


    public static void main(String[] args){
        try {
            //InputStream fileInputStream = file.getInputStream();
            String localFile = "a.xlsx";
            Workbook workbook = ExcelUtil.readExcel(localFile);

            String sheetName  = "all";
            String headerKey = "公司整体";
            logger.info("current sheet", sheetName);
            Sheet sheet = workbook.getSheet("5");
            boolean findHeaders = false;
            for (int rowNum = 0; rowNum < 10; rowNum ++) {
                Row row = sheet.getRow(rowNum);
                Iterator<Cell> ite = row.cellIterator();
                List<ExcelCell> excelHeaderList = new ArrayList<>();
                boolean allBlank = true;
                while(ite.hasNext()) {
                    Cell cell = ite.next();
                    String cellValue = convertCellValue(cell);
                    ExcelCell excelCell = new ExcelCell(rowNum, cell.getColumnIndex(), cellValue, cell.getCellTypeEnum().name());
                    excelHeaderList.add(excelCell);
                    if(!org.springframework.util.StringUtils.isEmpty(cellValue)) {
                        allBlank = false;
                    }
                    if(!findHeaders && cellValue.contains(headerKey)) {
                        findHeaders = true;
                    }
                }
                if (allBlank) {
                    logger.info("current row", "{} row number is blank!", rowNum);
                }
                if(!excelHeaderList.isEmpty() && !allBlank) {
                    if(findHeaders) {
                    } else {
                    }
                }
            }
            //System.out.println(JsonUtil.toJson());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




}
package Grid;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsvFileReader {
    public List<int[][]> AsvFileReaderHelper(String inputFileName){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(inputFileName));
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            List<int[][]> sudokuGrids = new ArrayList<>();

            for (int sheetIndex=0;sheetIndex<workbook.getNumberOfSheets();sheetIndex++){
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                int[][] sudokuGrid = new int[9][9];
                int rowIndex = 0;

                for (Row row:sheet){
                    int colIndex = 0;
                    for (Cell cell:row){
                        if (cell.getCellType() == CellType.NUMERIC){
                            int cellValue = (int) cell.getNumericCellValue();

                            if (cellValue != 369 && cellValue!=738){
                                sudokuGrid[rowIndex][colIndex] = cellValue;
                            }
                            colIndex++;
                        }
                    }
                    rowIndex++;

                    fileInputStream.close();
                }
                sudokuGrids.add(sudokuGrid);
                return sudokuGrids;

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Test
    public void Test(){
        List<int[][]> test = AsvFileReaderHelper("D:/Work/Metaverse_Sec.xlsx");
    }
}

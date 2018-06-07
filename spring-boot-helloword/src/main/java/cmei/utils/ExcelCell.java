package cmei.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * ExcelCell
 *
 * @author meicanhua
 * @create 2018-06-06 上午10:55
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class ExcelCell implements Serializable {

    private static final long serialVersionUID = 1130080797940896809L;

    private int col; //原始Excel表中的列

    private int row; //原始Excel表中的行

    private String value;

    @JsonIgnore
    private String typeName;

    public ExcelCell(int row, int col, String value, String typeName) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.typeName = typeName;
    }

}
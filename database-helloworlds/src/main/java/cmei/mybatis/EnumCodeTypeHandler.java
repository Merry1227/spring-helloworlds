package cmei.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 参考EnumOrdinalTypeHandler 实现内部定制的code到enum的转换
 *
 * @author meicanhua
 * @create 2017-03-15 下午1:19
 **/
public class EnumCodeTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private Class<E> type;
    private final Map<Integer, E> enumMap = new ConcurrentHashMap<Integer, E>();

    public EnumCodeTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }

        for (E e : enums) {
            if (e instanceof CodeEnum) {
                CodeEnum codeEnum = (CodeEnum) e;
                enumMap.put(codeEnum.getCode(), e);
            } else {
                enumMap.put(e.ordinal(), e);
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter instanceof CodeEnum) {
            CodeEnum codeEnum = (CodeEnum) parameter;
            ps.setInt(i, codeEnum.getCode());
        } else {
            ps.setInt(i, parameter.ordinal());
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);

        if (rs.wasNull()) return null;

        try {
            return enumMap.get(i);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);

        if (rs.wasNull()) return null;

        try {
            return enumMap.get(i);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);

        if (cs.wasNull()) return null;

        try {
            return enumMap.get(i);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
        }
    }
}

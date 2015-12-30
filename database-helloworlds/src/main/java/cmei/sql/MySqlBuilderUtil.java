package cmei.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MySqlBuilderUtil {	
	public static final String updateKey="id";
	private static final String mySqlTag_End=";";
	
	//MySql Escape Characters, see http://dev.mysql.com/doc/refman/5.5/en/string-syntax.html
	private static final String mySqlTag_Backslash="\\"; //Note: the first \ is java esc.
	private static final String mySqlTag_SigQuo="'";
	private static final String mySqlTag_DobQuo="\"";
	private static final String mySqlTag_Percent="%"; // when use  where...like, this character may be need escape.
	private static final String mySqlTag_UnderLine="_"; //when use where...like, this character may be need escape.
    //others... {\0,\n,\r,\\,\',",\Z ...... TBD when need them.
	//End

	/*
	 * Construct a mysql statement for insert. 
	 * Especially, when tableName or FieldMap is null or empty, it will return null.
	 * @Param FieldMap
	 * @Param tableName
	 * @Param orgID  unused temporarily.
	 */
	public static String buildInsertStatement(Map<String,String> FieldMap,String tableName,long orgID)throws MySqlBuildException{
		String sql=null;
		
		if(tableName==null||FieldMap==null||FieldMap.isEmpty()){
			throw new MySqlBuildException("tableName or FieldMap is null or empty.");
		}
		
		StringBuffer colNames=new StringBuffer();
		StringBuffer colValues=new StringBuffer();
		//search FieldName and FieldValue from FieldMap
		//key:value is not validated when they are null.
		Iterator ite=FieldMap.entrySet().iterator();
		while(ite.hasNext()){
			Entry entry=(Entry)ite.next();
			String colName=escapseChars4MySql((String)entry.getKey()); 
			String colValue=escapseChars4MySql((String)entry.getValue());	
			colNames.append(colName+",");
			if(colValue!=null)
				colValues.append(mySqlTag_SigQuo+colValue+mySqlTag_SigQuo+",");	 
			else
				colValues.append(colValue+",");
			}
			 
		colNames.delete(colNames.length()-1, colNames.length());
		colValues.delete(colValues.length()-1, colValues.length());
		
		//add for Escape Characters 2011.06.23
		String _tableName=escapseChars4MySql(tableName);
		sql="insert into "+_tableName+"("+colNames+")"+" values("+colValues+")"+MySqlBuilderUtil.mySqlTag_End;
	    return sql;	
		}
	
	
	// change update interface. 2011.06.28
	public static String buildUpdateStatement(Map<String,String> FieldMap,String tableName, long orgID) throws MySqlBuildException{
		String sql=null;
		if(tableName==null||FieldMap==null||FieldMap.isEmpty()){
			throw new MySqlBuildException("tableName or FieldMap is null or empty.");
		}
		if(!FieldMap.containsKey(updateKey)){
			throw new MySqlBuildException("no such key: "+updateKey+" in FieldMap.");
		}
		String idValue=FieldMap.get(updateKey);
		List<String> keyValue=new ArrayList<String>();
		keyValue.add(idValue);
		
		Map<String,String> newFieldMap=new HashMap<String,String>();
		newFieldMap.putAll(FieldMap);
		newFieldMap.remove(updateKey);
		
		return buildUpdateStatement(newFieldMap,tableName,orgID,updateKey,keyValue);	
	}
	
	/*
	 * construct a mysql statement for update 
	 * @Param FieldMap
	 * @Param tableName
	 * @Param orgID  unused temporarily.
	 * @Param key and keyValues are used to express where Conditions: key in keyValues.
	 */	
	private static String buildUpdateStatement(Map<String,String> FieldMap,String tableName, long orgID,String key,List<String> keyValues)throws MySqlBuildException{
		// if colNameMap is null or empty,return null
		String sql=null;
		if(tableName==null||FieldMap==null||FieldMap.isEmpty()){
			throw new MySqlBuildException("tableName or FieldMap is null or empty.");
		}	
		
		//search FieldName and FieldValue from FieldMap
		//key:value is not validated when they are null.
		StringBuffer colNameValues=new StringBuffer();
		Iterator ite=FieldMap.entrySet().iterator();
		while(ite.hasNext()){
			Entry entry=(Entry)ite.next();
			String colName=escapseChars4MySql((String)entry.getKey()); 
			String colValue=escapseChars4MySql((String)entry.getValue()); 
			colNameValues.append(colName+"="+mySqlTag_SigQuo+colValue+mySqlTag_SigQuo+",");
			}	 
		colNameValues.delete(colNameValues.length()-1, colNameValues.length());
		
		//construct the where condition 
	    // if key or keyValues is null or empty,will update all related rows. To Be Discuss.
		StringBuffer whereStatement=new StringBuffer();
		if(key!=null&&keyValues!=null&&!keyValues.isEmpty()){
			whereStatement.append(" where "+key+" in (");
			for(String keyValue:keyValues){
				keyValue=escapseChars4MySql(keyValue);  
				whereStatement.append(mySqlTag_SigQuo+keyValue+mySqlTag_SigQuo+",");
			}
			whereStatement.delete(whereStatement.length()-1,whereStatement.length());
			whereStatement.append(")");	
		}
		
		String _tableName=escapseChars4MySql(tableName);  
		sql="update "+_tableName+" set "+colNameValues+whereStatement+MySqlBuilderUtil.mySqlTag_End;
	    return sql;
	    }
	
	
	public static String buildDeleteStatement(String tableName,long orgID,String key,List<Long> keyValues)throws MySqlBuildException{
		final String deleteFieldName="is_deleted";
		final String FieldValue="true";
	
		String sql=null;
		if(tableName==null||key==null||keyValues==null){
			throw new MySqlBuildException("tableName or prikey is null.");
		}		
		
		//construct the where condition 
	    // if key or keyValues is null or empty,will update all related rows. To Be Discuss.
		StringBuffer whereStatement=new StringBuffer();
        whereStatement.append(" where "+key+" in (");
		for(Long keyValue:keyValues){
				whereStatement.append(keyValue).append(",");
		}
	   if(!keyValues.isEmpty()){
				whereStatement.delete(whereStatement.length()-1,whereStatement.length());
		}
		whereStatement.append(")");	
		
		String _tableName=escapseChars4MySql(tableName); 
		sql="update "+_tableName+" set "+deleteFieldName+"="+FieldValue+whereStatement+MySqlBuilderUtil.mySqlTag_End;
	    return sql;
	}
	
	private static String escapseChars4MySql(String primitiveStr){
		
		if(primitiveStr==null) return null;
		
		String convertStr=new String(primitiveStr);
		
	    final String _Backslash="\\\\"; // there is no need to conver \,because the java user have to convet it before pass such parameters.
		final String _SigQuo="\\'";
		final String _DobQuo="\\\"";	
      // must convert from \ first
		if(convertStr.contains(mySqlTag_Backslash)){
			StringBuffer sb=new StringBuffer(convertStr);
			convertStr=convertStr.replace(mySqlTag_Backslash,_Backslash);
            //System.out.println("back:"+convertStr);
		}
		if(convertStr.contains(mySqlTag_SigQuo)){
			convertStr=convertStr.replace(mySqlTag_SigQuo,_SigQuo);
			//System.out.println("Sq:"+convertStr);
		}
		if(convertStr.contains(mySqlTag_DobQuo)){
		    convertStr=convertStr.replace(mySqlTag_DobQuo,_DobQuo);
		   // System.out.println("Dq:"+convertStr);
		}
 
		return convertStr;
	}
}

package cmei.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestMysql {
	String diverName="com.mysql.jdbc.Driver";
	String url="jdbc:mysql://s1db-master-uat.dev.alert.com:3308/alert";
	String username="alert";
	String password="ekWA62uG";
	
	public Connection getConnect(){
		Connection con=null;
		try{
		    Class.forName(diverName);
		    con=DriverManager.getConnection(url,username,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;		
	}
	
	public void execute(){
		Map<String, String> colNameMap=new HashMap<String,String>();
		String tableName="personInfo1";
		String[] colNames={"Pname","Ptel","Pemail"};
		//String[] colValues={"na,m\te\\1's","700000","1233%45\"@#%$*!;6\t\'_6\n"};
		String[] colValues={"na,m\t","001",null};
		colNameMap.put(colNames[0],colValues[0]);
		colNameMap.put(colNames[1],colValues[1]);
		colNameMap.put(colNames[2],colValues[2]);
		
		try{
			//insert
			String sql="select * from catalogs where id=1";
				//MySqlBuilderUtil.buildInsertStatement(colNameMap,tableName,0);
			System.out.println(sql);
			//sql="insert into personInfo1(Pname,Pemail,Ptel) values('n\\'1,11','n%@13\\\\3_3','12334566');";
			Statement  st=this.getConnect().createStatement();
			ResultSet rs=st.executeQuery(sql);
			
			if(rs!=null){
				rs.next();
				Object h=rs.getObject("create_time");
				if(h instanceof Date){
					Date s=rs.getDate("create_time");
					System.out.println(s);
				}else{
					System.out.println(h.getClass());
				}
				
				Object ht=rs.getObject("update_time");
				if(h instanceof Date){
					Date s=rs.getDate("update_time");
					System.out.println(s);
				}else{
					System.out.println(ht.getClass());
				}
				
			}
			//update
//			List<String> colValueList=new ArrayList();
//			colValueList.add(colValues[0]);
//			colNameMap.put(colNames[1],"0000");
//			String sql2=MySqlBuilderUtil.buildUpdateStatement(colNameMap, tableName, 0);
//			System.out.println(sql2);
//			PreparedStatement  st2=this.getConnect().prepareStatement(sql2);
//			st2.executeUpdate();
            rs.close();
			this.getConnect().close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

	
	public static void main(String args[]){
		TestMysql mysql=new TestMysql();
		mysql.execute();	
	}
	

}

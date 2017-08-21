package com.zkname.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zkname.generator.provider.db.table.TableFactory;
import com.zkname.generator.provider.db.table.model.Column;
import com.zkname.generator.provider.db.table.model.Table;

public class TableHTML {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		StringBuffer sb=new StringBuffer("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>TABLE</title></head><body>");
		
		
		List<Table> tables = TableFactory.getInstance().getAllTables();
		List exceptions = new ArrayList();
		sb.append("<table border=\"1\">");
		for(int i = 0; i < tables.size(); i++ ) {
			Table table = tables.get(i);
			sb.append("<tr>");
			sb.append("<td><a href=\"#"+table.getSqlName()+"\">").append(table.getSqlName()).append("</a></td>");
			sb.append("<td><a href=\"#"+table.getSqlName()+"\">"+table.getRemarks()+"</a></td>");
			sb.append("</tr>");
		}
		sb.append("</table>&nbsp;</br>&nbsp;");
		
		
		for(int i = 0; i < tables.size(); i++ ) {
			Table table = tables.get(i);
			sb.append("<table id=\""+table.getSqlName()+"\">");
			sb.append("<tr>");
			sb.append("<td>").append(table.getSqlName()).append("：").append(table.getRemarks()).append("</td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			
			sb.append("<tr>");
			sb.append("<td colspan=\"2\"><table border=\"1\"  width=\"100%\" >");
			
			sb.append("<tr>");
			sb.append("<td><div align=\"center\"><strong>字段名</strong></div></td>");
			sb.append("<td><div align=\"center\"><strong>字段类型</strong></div></td>");
			sb.append("<td><div align=\"center\"><strong>字段注释</strong></div></td>");
			sb.append("</tr>");
			Iterator<Column> iterator=table.getColumns().iterator();
			while (iterator.hasNext())
			  {
				Column column = (Column) iterator.next();
				sb.append("<tr>");
				sb.append("<td>"+column.getColumnName()+"</td>");
				sb.append("<td>"+column.getJdbcType()+"("+column.getSize()+")"+"   "+(column.isPk()?"PK":"")+(column.isFk()?"FK":"")+"</td>");
				sb.append("<td>"+column.getRemarks()+"</td>");
				
				sb.append("</tr>");
			  }

			sb.append("</table></td>");
			sb.append("</tr>");
			  
			sb.append("</table>");
			sb.append("&nbsp;</br><div style=\"margin:0;padding:0; width:100%;height:1px;background-color:#303030;overflow:hidden;\"> </div>&nbsp;");
		}
		sb.append("</body></html>");
		
		new File(GeneratorProperties.getRequiredProperty("outRoot")).mkdirs();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GeneratorProperties.getRequiredProperty("outRoot")+File.separatorChar+"TableHTML.html")));

	     writer.write(sb.toString());
	     
	     writer.close();
	     
	     
	     Runtime.getRuntime().exec("cmd.exe /c start "+GeneratorProperties.getRequiredProperty("outRoot"));
	}

}

package com.zkname.generator;


public class GeneratorMain {
	/**
	 * 请直接修改以下代码调用不同的方法以执行相关生成任务.
	 */
	public static void main(String[] args) throws Exception {
		GeneratorProperties.PROPERTIES_FILE_NAMES = new String[]{"generator.xml"};
		GeneratorProperties.reload();
		GeneratorFacade g = new GeneratorFacade();
//		g.printAllTableNames();				//打印数据库中的表名称
		g.deleteOutRootDir();							//删除生成器的输出目录
		g.generateByTable("sys_region","template"); //通过数据库表生成文件,template为模板的根目录
//		g.generateByAllTable("template"); //生成所有
//		g.generateByAllTable("template","yy");	//自动搜索数据库中的所有表并生成文件,template为模板的根目录
//		g.deleteByTable("table_name", "template"); //删除生成的文件
		//打开文件夹
		Runtime.getRuntime().exec("cmd.exe /c start "+GeneratorProperties.getRequiredProperty("outRoot"));
	}
}


package com.zkname.hd.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamDrainer implements Runnable
{
	private static Logger logger = LoggerFactory.getLogger(StreamDrainer.class);

	InputStream ins;
	StringBuffer pw;
	PrintWriter pwa;

	public StreamDrainer(InputStream ins, StringBuffer pw)
	{
		this.ins = ins;
		this.pw = pw;
	}
	
	public StreamDrainer(InputStream ins, PrintWriter pw)
	{
		this.ins = ins;
		this.pwa = pw;
	}

	public void run()
	{
		boolean isDebug = logger.isDebugEnabled();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "gbk"));
			String line = null;

			while ((line = reader.readLine()) != null)
			{
				if (isDebug)
				{
					if(pw!=null){
						pw.append("<br>" + line);
					}else if(pwa!=null){
						pwa.println("<br>" + line);
					}else{
						System.out.println(line);
					}
				}
			}
		}
		catch (Exception e)
		{
			if (logger.isErrorEnabled())
			{
				if(pw!=null){
					pw.append("<br>读取异常" + e.getMessage());
				}else if(pwa!=null){
					pwa.println("<br>读取异常" + e.getMessage());
				}else{
					System.out.println("<br>读取异常" + e.getMessage());
				}
			}
		}
		if (isDebug)
		{
			if(pw!=null){
				pw.append("<br>打印结束");
			}else if(pwa!=null){
				pwa.println("<br>打印结束");
			}else{
				System.out.println("打印结束");
			}
		}
	}

}

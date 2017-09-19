package com.zkname;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.zkname.redis.ListOps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@ComponentScan("com.zkname")
public class NoSqlTest {

	@Autowired
	private ListOps listops;
	
	/**
	 * 队列实现
	 */
	@Test
	public void queue() {
		/**
		 * 先进先出
		 */
		List<String> list=Lists.newArrayList();
		for (int i = 0; i < 15; i++) {
			list.add(i + "");
		}
		listops.push("key",list.toArray(new String[list.size()]));
		
		listops.push("key","123");
		listops.push("key","124");
		listops.push("key","125");
		listops.push("key","126");
		listops.push("key","127");
		
		for (int i = 0; i < 20; i++) {
			System.out.println(listops.out("key"));
			if(i%5==0){
				System.out.println("length===" + listops.length("key"));
			}
		}
	}

}

package cn.sen.springboot;

import cn.sen.springboot.configration.Person;
import cn.sen.springboot.configration.Person2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo02ApplicationTests {

	@Autowired
	private Person person;
	@Autowired
	private Person2 person2;

	@Test
	public void contextLoads() {
//		System.out.println(person);
		System.out.println(person2);
	}


}

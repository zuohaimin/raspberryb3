package cn.edu.swpu.raspberryb3;

import cn.edu.swpu.raspberryb3.dal.RedisDal;
import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Raspberryb3ApplicationTests {

	@Autowired
	private RedisDal redisDal;

	@Test
	public void contextLoads() {
//		DHMessage dhMessage = new DHMessage();
//		dhMessage.setHumidity("100");
//		dhMessage.setTempetature("26");
//		DHMessage dhMessageSave = redisDal.save(dhMessage);
//		Assert.assertNotNull(dhMessage);
	}

}

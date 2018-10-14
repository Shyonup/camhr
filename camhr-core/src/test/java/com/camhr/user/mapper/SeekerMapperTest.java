package com.camhr.user.mapper;

import com.camhr.resume.entity.Seeker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SeekerMapperTest {

  @Autowired
  private SeekerMapper seekerMapper;

  //无数据增加的方法，id先写死测试一下
  @Test
  public void getSeekerById() {
    Seeker seeker = seekerMapper.getSeekerById(29540);
    Assert.assertTrue(seeker != null);
  }

  //无数据增加的方法，id先写死测试一下
  @Test
  public void update() {
    int row = seekerMapper.updateWorkStatus(2,25835);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void addSeeker(){
    Seeker seeker = new Seeker();
    seeker.setRegionCode("86");
    seeker.setName("haha");
    seeker.setMobile("911");
    seeker.setPassword("");
    seeker.setEmail("");
    int rows = seekerMapper.addSeeker(seeker);
    Assert.assertEquals(1,rows);
  }
}

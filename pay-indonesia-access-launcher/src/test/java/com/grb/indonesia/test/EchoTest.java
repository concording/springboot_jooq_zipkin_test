//package com.grb.indonesia.test;
//
//        import org.junit.Test;
//        import org.junit.runner.RunWith;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.test.SpringApplicationConfiguration;
//        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import com.grb.indonesia.dao.EchoDao;
//import com.grb.indonesia.entity.EchoEntity;
//import com.grb.indonesia.App;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(App.class)
//public class EchoTest{
//
//    @Autowired
//    EchoDao echoDao;
//
//    @Test
//    public void testSelect(){
//
//
//        List<EchoEntity> list = echoDao.findAll();
//
//        System.out.println(list);
//    }
//
//}
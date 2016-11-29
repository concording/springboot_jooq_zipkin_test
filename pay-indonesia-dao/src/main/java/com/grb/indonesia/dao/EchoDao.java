//package com.grb.indonesia.dao;
//
//        import tk.mybatis.mapper.common.Mapper;
//        import org.apache.ibatis.annotations.SelectProvider;
//        import org.apache.ibatis.annotations.Select;
//
//import com.grb.indonesia.entity.EchoEntity;
//import com.grb.indonesia.dao.provider.EchoProvider;
//
//        import java.util.List;
//
//public interface EchoDao extends Mapper<EchoEntity> {
//
//    @SelectProvider(type = EchoProvider.class, method = "selectById")
//    EchoEntity selectById(EchoEntity echoEntity);
//
//    @Select("select * from echo_info")
//    List<EchoEntity> findAll();
//
//
//
//}
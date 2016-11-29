//package com.grb.indonesia.dao.provider;
//
//        import org.apache.ibatis.jdbc.SQL;
//import com.grb.indonesia.entity.EchoEntity;
//
//public class EchoProvider{
//    public final static String TABLE_NAME = "echo_info";
//    public final static String COLUMNS = "id, echoContent";
//
//    public String selectById(final EchoEntity echoEntity){
//        SQL sql = new SQL() {
//            {
//                SELECT(COLUMNS);
//                FROM(TABLE_NAME);
//                WHERE("id=#id");
//            }
//        };
//        return sql.toString();
//    }
//
//}
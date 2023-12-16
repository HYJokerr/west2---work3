package OrderManagement.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Select {

    //查询商品并以id排序
    public static void goodsIdSelect()throws Exception{

        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            //定义sql
            String sql = "SELECT id,name,price FROM t_goods";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //设置参数

            //执行
            rs = ps.executeQuery();

            //输出结果

            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品名字:" + rs.getString("name"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

            //释放资源
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    //查询订单并以id排序
    public static void orderIdSelect(){

        try {
            //链接
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

            //事物管理
            conn.setAutoCommit(false);

            //定义sql
            String sql = "SELECT id,goodsid,number,time,price FROM t_order";

            //获取PreparedStatement对象
            PreparedStatement ps = conn.prepareStatement(sql);

            //设置参数


            //执行
            ResultSet rs = ps.executeQuery();

            //输出结果

            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品:" + rs.getInt("goodsid"));
                System.out.println("商品数量:" + rs.getString("number"));
                System.out.println("下单时间:" + rs.getTimestamp("time"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

            //释放资源
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


}

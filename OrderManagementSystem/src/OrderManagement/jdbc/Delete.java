package OrderManagement.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Delete {

    //删除商品
    public static void goodsDelete(int id)throws Exception{

        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;

        try {
            //开启事务
            conn.setAutoCommit(false);

            //定义sql
            String sql1 = "delete from t_goods where id = ?";
            String sql2 = "SELECT goodsid FROM t_order";

            //获取PreparedStatement对象
            ps1 = conn.prepareStatement(sql1);
            ps2 = conn.prepareStatement(sql2);

            //设置参数
            ps1.setInt(1,id);

            //判断
            rs = ps2.executeQuery();
            while(rs.next()) {
                if (rs.getInt("goodsid") ==id){
                    System.out.println("删除的商品在订单中存在!");
                    throw new Exception();
                }
            }

            //执行
            int n = ps1.executeUpdate();
            if (n > 0) {
                System.out.println("删除成功!");
            }

            //提交事物
            conn.commit();

            //释放资源
            rs.close();
            ps2.close();
            ps1.close();
            conn.close();

        } catch (Exception ex) {
            //回滚事务
            conn.rollback();
            ex.printStackTrace();
        }

    }

    //删除订单
    public static void orderDelete(int id)throws Exception{

        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps1 = null;


        try {
            //开启事务
            conn.setAutoCommit(false);

            //定义sql
            String sql1 = "delete from t_order where id = ?";

            //获取PreparedStatement对象
            ps1 = conn.prepareStatement(sql1);

            //设置参数
            ps1.setInt(1,id);

            //执行
            int n = ps1.executeUpdate();
            if (n > 0) {
                System.out.println("删除成功!");
            }

            //提交事物
            conn.commit();

            //释放资源
            ps1.close();
            conn.close();

        } catch (Exception ex) {
            //回滚事务
            conn.rollback();
            ex.printStackTrace();
        }

    }

}

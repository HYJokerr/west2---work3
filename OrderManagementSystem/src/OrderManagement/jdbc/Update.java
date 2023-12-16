package OrderManagement.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Update {

    //更新商品信息
    public static void goodsUpdate(int id,String name,double price) throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");
        PreparedStatement ps = null;

        try {
            //开启事务
            conn.setAutoCommit(false);

            //定义sql
            String sql = "update `t_goods` set `name`=?,`price`=? where `id`=?";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //设置参数
            ps.setString(1,name);
            ps.setDouble(2,price);
            ps.setInt(3, id);


            //执行
            int n= ps.executeUpdate();
            if (n > 0) {
                System.out.println("修改成功!");
            }

            //提交事物
            conn.commit();

            //释放资源
            ps.close();
            conn.close();

        } catch (SQLException e) {
            //回滚事务
            conn.rollback();
            e.printStackTrace();
        }
    }


    //更新订单信息
    public static void orderUpdate(int id,int goodsid,int number,double price) throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");
        PreparedStatement ps = null;

        try {
            //开启事务
            conn.setAutoCommit(false);

            //定义sql
            String sql = "update `t_order` set `goodsid`=?,`number`=?,`price`=?where `id`=?";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //设置参数
            ps.setInt(1,goodsid);
            ps.setInt(2,number);
            ps.setDouble(3,price);
            ps.setInt(4, id);


            //执行
            int n= ps.executeUpdate();
            if (n > 0) {
                System.out.println("修改成功!");
            }

            //提交事物
            conn.commit();

            //释放资源
            ps.close();
            conn.close();

        } catch (SQLException e) {
            //回滚事务
            conn.rollback();
            e.printStackTrace();
        }
    }
}

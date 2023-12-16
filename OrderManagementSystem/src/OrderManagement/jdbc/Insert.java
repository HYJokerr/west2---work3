package OrderManagement.jdbc;

import java.sql.*;


public class Insert {

    //添加商品
    public static void goodsInsert(int id, String name, double price)throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;

        try {
            //开启事务
            conn.setAutoCommit(false);

            //定义sql
            String sql = "insert into t_goods(`id`,`name`,`price`) values (?,?,?)";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //设置参数
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);

            //执行
            int n = ps.executeUpdate();

            //输出结果
            if (n > 0) {
                System.out.println("成功添加商品!");
            }


            //提交事物
            conn.commit();

            //释放资源
            ps.close();
            conn.close();

        } catch (Exception ex) {
            //回滚事务
            conn.rollback();
            ex.printStackTrace();
        }
    }

    //添加订单
    public static void orderInsert(int id, int goodsId, int number, double price)throws Exception{
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
            String sql1 = "insert into t_order(`id`,`goodsid`,`number`,`time`,`price`) values (?,?,?,?,?)";
            String sql2 = "select id from t_goods";

            //获取PreparedStatement对象
            ps1 = conn.prepareStatement(sql1);
            ps2 = conn.prepareStatement(sql2);

            //设置参数
            ps1.setInt(1, id);
            ps1.setInt(2, goodsId);
            ps1.setInt(3, number);
            ps1.setTimestamp(4, new Timestamp(System.currentTimeMillis()) );
            ps1.setDouble(5, price);

            //验证数据 检查商品是否存在，价格是否合法
            rs = ps2.executeQuery();
            boolean flag=false;
            while(rs.next()) {
                if(rs.getInt("id")==goodsId){
                    flag=true;
                }
            }
            if(!flag){
                System.out.println("商品不存在!");
                throw new Exception();
            }
            if(price<=0){
                System.out.println("商品价格不合法!");
                throw new Exception();
            }


            //执行
            int n = ps1.executeUpdate();
            //输出结果
            if (n > 0) {
                System.out.println("成功添加订单!");
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

}

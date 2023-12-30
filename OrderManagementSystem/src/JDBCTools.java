import java.sql.*;

public class JDBCTools {

    // 添加商品
    public static void goodsInsert(Goods goods)throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs1 = null;

        try {
            //开启事务
            conn.setAutoCommit(false);

            //定义sql
            String sql1 = "insert into t_goods(`id`,`name`,`price`) values (?,?,?)";
            String sql2 = "select id,name from t_goods";

            //获取PreparedStatement对象
            ps1 = conn.prepareStatement(sql1);
            ps2 = conn.prepareStatement(sql2);

            //设置参数
            ps1.setInt(1, goods.getId());
            ps1.setString(2, goods.getName());
            ps1.setDouble(3, goods.getPrice());

            //判断
            rs1 = ps2.executeQuery();
            while(rs1.next()) {
                if (rs1.getInt("id") == goods.getId()){
                    System.out.println("添加的商品id已存在!");
                    throw new Exception();
                }
                if(rs1.getString("name").equals(goods.getName())){
                    System.out.println("添加的商品名字重复!");
                    throw new Exception();
                }
            }

            //执行
            int n = ps1.executeUpdate();

            //输出结果
            if (n > 0) {
                System.out.println("成功添加商品!");
            }


            //提交事物
            conn.commit();
        } catch (Exception ex) {
            //回滚事务
            conn.rollback();
            ex.printStackTrace();
        }finally {
            //释放资源
            rs1.close();
            ps2.close();
            ps1.close();
            conn.close();
        }
    }

    // 添加订单
    public static void orderInsert(Order order)throws Exception{
        // 链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;

        try {
            // 开启事务
            conn.setAutoCommit(false);

            // 定义sql
            String sql1 = "insert into t_order(`id`,`goodsid`,`number`,`time`,`price`) values (?,?,?,?,?)";
            String sql2 = "select id,price from t_goods";

            // 获取PreparedStatement对象
            ps1 = conn.prepareStatement(sql1);
            ps2 = conn.prepareStatement(sql2);

            // 设置参数
            ps1.setInt(1, order.getId());
            ps1.setInt(2, order.getGoodsid());
            ps1.setInt(3, order.getNumber());
            ps1.setTimestamp(4, order.getTime() );
            ps1.setDouble(5, order.getPrice());

            // 验证数据
            // 检查商品是否存在
            rs = ps2.executeQuery();
            boolean flag=false;
            double p = 0;
            while(rs.next()) {
                if(rs.getInt("id")==order.getGoodsid()){
                    flag=true;
                    p=rs.getDouble("price");
                }
            }
            if(!flag){
                System.out.println("商品不存在!");
                throw new Exception();
            }
            // 订单价格 不等于 商品单价乘数量 则不合法
            if(order.getPrice()!=p*order.getNumber()){
                System.out.println("商品价格不合法!");
                throw new Exception();
            }

            // 执行
            int n = ps1.executeUpdate();
            // 输出结果
            if (n > 0) {
                System.out.println("成功添加订单!");
            }

            // 提交事物
            conn.commit();
        } catch (Exception ex) {
            // 回滚事务
            conn.rollback();
            ex.printStackTrace();
        }finally {
            // 释放资源
            rs.close();
            ps2.close();
            ps1.close();
            conn.close();
        }
    }

    // 删除商品
    public static void goodsDelete(Goods goods)throws Exception{

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
            ps1.setInt(1,goods.getId());

            //判断
            rs = ps2.executeQuery();
            while(rs.next()) {
                if (rs.getInt("goodsid") == goods.getId()){
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

        } catch (Exception ex) {
            //回滚事务
            conn.rollback();
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps2.close();
            ps1.close();
            conn.close();
        }

    }

    // 删除订单
    public static void orderDelete(Order order)throws Exception{

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
            ps1.setInt(1,order.getId());

            //执行
            int n = ps1.executeUpdate();
            if (n > 0) {
                System.out.println("删除成功!");
            }

            //提交事物
            conn.commit();

        } catch (Exception ex) {
            //回滚事务
            conn.rollback();
            ex.printStackTrace();
        }finally {
            //释放资源
            ps1.close();
            conn.close();
        }
    }

    // 修改商品信息
    public static void goodsUpdate(Goods newGoods) throws Exception{
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
            ps.setString(1,newGoods.getName());
            ps.setDouble(2,newGoods.getPrice());
            ps.setInt(3, newGoods.getId());


            //执行
            int n= ps.executeUpdate();
            if (n > 0) {
                System.out.println("修改成功!");
            }

            //提交事物
            conn.commit();


        } catch (Exception e) {
            //回滚事务
            conn.rollback();
            e.printStackTrace();
        } finally {
            //释放资源
            ps.close();
            conn.close();

        }
    }

    // 修改订单信息
    public static void orderUpdate(Order newOrder) throws Exception{
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
            ps.setInt(1,newOrder.getGoodsid());
            ps.setInt(2,newOrder.getNumber());
            ps.setDouble(3,newOrder.getPrice());
            ps.setInt(4, newOrder.getId());

            //执行
            int n= ps.executeUpdate();
            if (n > 0) {
                System.out.println("修改成功!");
            }

            //提交事物
            conn.commit();

        } catch (Exception e) {
            //回滚事务
            conn.rollback();
            e.printStackTrace();
        }finally {
            //释放资源
            ps.close();
            conn.close();

        }
    }

    // 查询商品信息
    public static void goodsSelect()throws Exception{

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
            System.out.println("商品信息:");
            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品名字:" + rs.getString("name"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps.close();
            conn.close();
        }
    }

    // 查询订单信息
    public static void orderSelect() throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {


            //事物管理
            conn.setAutoCommit(false);

            //定义sql
            String sql = "SELECT o.id,name,number,time,o.price " +
                    "FROM t_order AS o " +
                    "INNER JOIN t_goods AS g ON o.goodsid = g.id";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //执行
            rs = ps.executeQuery();

            //输出结果
            System.out.println("订单信息:");
            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品:" + rs.getString("name"));
                System.out.println("商品数量:" + rs.getInt("number"));
                System.out.println("下单时间:" + rs.getTimestamp("time"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps.close();
            conn.close();
        }
    }

    // 商品按价格降序排序
    public static void goodsOrderByPriceDesc() throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            //定义sql
            String sql = "SELECT id,name,price FROM t_goods " +
                    "ORDER BY price DESC ";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //设置参数

            //执行
            rs = ps.executeQuery();

            //输出结果
            System.out.println("商品信息(价格降序排序):");
            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品名字:" + rs.getString("name"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps.close();
            conn.close();
        }


    }

    // 订单按下单时间降序排序
    public static void orderOrderByTimeDesc() throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {


            //事物管理
            conn.setAutoCommit(false);

            //定义sql
            String sql = "SELECT o.id,name,number,time,o.price " +
                    "FROM t_order AS o " +
                    "INNER JOIN t_goods AS g ON o.goodsid = g.id " +
                    "ORDER BY time DESC";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);

            //执行
            rs = ps.executeQuery();

            //输出结果
            System.out.println("订单信息(时间降序排序):");
            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品:" + rs.getString("name"));
                System.out.println("商品数量:" + rs.getInt("number"));
                System.out.println("下单时间:" + rs.getTimestamp("time"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps.close();
            conn.close();
        }

    }

    // 分页查询商品 page是页数 limit是每页的数据数
    public static void goodsLimitSelect(int page,int limit)throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;
        ResultSet rs = null;



        try {

            //定义sql
            String sql = "SELECT id,name,price FROM t_goods LIMIT ?,?";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1,(page-1)*limit);
            ps.setInt(2,limit);
            //设置参数

            //执行
            rs = ps.executeQuery();

            //输出结果
            System.out.println("商品信息:");
            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品名字:" + rs.getString("name"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps.close();
            conn.close();
        }




    }

    // 分页查询订单
    public static void orderLimitSelect(int page,int limit)throws Exception{
        //链接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system?useSSL=false", "root", "123654");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //事物管理
            conn.setAutoCommit(false);

            //定义sql
            String sql = "SELECT o.id,name,number,time,o.price " +
                    "FROM t_order AS o " +
                    "INNER JOIN t_goods AS g ON o.goodsid = g.id " +
                    "LIMIT ?,?";

            //获取PreparedStatement对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1,(page-1)*limit);
            ps.setInt(2,limit);

            //执行
            rs = ps.executeQuery();

            //输出结果
            System.out.println("订单信息:");
            while (rs.next()) {
                System.out.println("编号:" + rs.getInt("id"));
                System.out.println("商品:" + rs.getString("name"));
                System.out.println("商品数量:" + rs.getInt("number"));
                System.out.println("下单时间:" + rs.getTimestamp("time"));
                System.out.println("价格:" + rs.getDouble("price"));
                System.out.println("--------------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            rs.close();
            ps.close();
            conn.close();
        }
    }

}

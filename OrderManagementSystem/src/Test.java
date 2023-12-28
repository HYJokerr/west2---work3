import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws Exception{
        Goods goods1 = new Goods(1,"Apple",10);
        Goods goods2 = new Goods(2,"Orange",5);
        Goods goods3 = new Goods(3,"ApplePan",5);
        Goods goods4 = new Goods(1,"Apple",11);

        Order order1 = new Order(1,1,10, new Timestamp(System.currentTimeMillis()),100.);
        Order order2 = new Order(2,10,10, new Timestamp(System.currentTimeMillis()),100.);
        Order order3 = new Order(3,1,10, new Timestamp(System.currentTimeMillis()),50.);
        Order order4 = new Order(2,1,5, new Timestamp(System.currentTimeMillis()),50.);
        Order order5 = new Order(3,1,2, new Timestamp(System.currentTimeMillis()),20.);



        // Insert

        // 成功添加商品good1,goods2
        //JDBCTools.goodsInsert(goods1);
        //JDBCTools.goodsInsert(goods2);
        // id重复的商品goods3添加失败
        //JDBCTools.goodsInsert(goods3);

        // 添加订单order1,order5
        //JDBCTools.orderInsert(order1);
        //JDBCTools.orderInsert(order5);
        // 添加订单order2商品不存在
        //JDBCTools.orderInsert(order2);
        // 添加订单order3价格不合法
        //JDBCTools.orderInsert(order3);


        /*---------------------------------------------------------------------------------*/

        // Delete
        // 成功删除商品goods2
        //JDBCTools.goodsDelete(goods2);
        // 要删除的商品goods1在订单中存在
        //JDBCTools.goodsDelete(goods1);

        // 成功删除订单order2
        //JDBCTools.orderDelete(order2);

        /*---------------------------------------------------------------------------------*/

        // Update
        // 更改商品信息
        //JDBCTools.goodsUpdate(goods4);
        // 更改订单信息
        //JDBCTools.orderUpdate(order4);

        /*---------------------------------------------------------------------------------*/

        // Select
        //JDBCTools.goodsSelect();
        //JDBCTools.orderSelect();
        // 商品按价格降序排序
        //JDBCTools.goodsOrderByPriceDesc();
        // 订单按时间降序排序
        //JDBCTools.orderOrderByTimeDesc();

        // 分页查询
        //JDBCTools.goodsLimitSelect(1,1);
        //JDBCTools.orderLimitSelect(1,1);

    }
}

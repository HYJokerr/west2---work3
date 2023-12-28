import java.sql.Timestamp;

public class Order {
    private int id;
    private int goodsid;
    private int number;
    private java.sql.Timestamp time;
    private double price;

    public Order(int id,int goodsid,int number, java.sql.Timestamp time, double price){
        this.id=id;
        this.goodsid=goodsid;
        this.number=number;
        this.time=time;
        this.price=price;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public int getNumber() {
        return number;
    }

    public Timestamp getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }

}

package ku.cs.shop.models;

public class MemberCard {

    private String name;
    private String phone;
    private double cumulativePurchase;
    private int stamp;
    private String imagePath;

    public MemberCard(String name, String phone, int stamp) {
        this.name = name;
        this.phone = phone;
        this.stamp = stamp;
        cumulativePurchase = 0;
        this.imagePath = getClass().getResource("/ku/cs/images/default.png").toExternalForm();
        System.out.println(imagePath);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public double getCumulativePurchase() {
        return cumulativePurchase;
    }

    public int getStamp() {
        return stamp;
    }


    public void addPurchase(double purchase) {
        cumulativePurchase += purchase;
        stamp += purchase / 50;
    }

    public boolean useStamp(int stamp) {
        if (this.stamp >= stamp) {
            this.stamp -= stamp;
            return true;
        }
        return false;
    }

    public void setCumulativePurchase(double cumulativePurchase) {
        this.cumulativePurchase = cumulativePurchase;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MemberCard(String name, String phone) {
        this(name, phone, 0);
    }

    @Override
    public String toString() {
        return name + " [" + stamp + " point]";
    }


}

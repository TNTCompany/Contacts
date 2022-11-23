package edu.jmu.wylcon.ui.personc;

public class Address {
    private String stamp;
    private String province;
    private String city;
    private String address;

    public Address(String stamp, String province, String city, String address) {
        this.stamp = stamp;
        this.province = province;
        this.city = city;
        this.address = address;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "stamp='" + stamp + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}


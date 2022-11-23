package edu.jmu.wylcon.ui.personc;

import java.util.ArrayList;

public class Person implements Comparable<Person>{
    private int id;
    private String name;

    private PhoneNumber phoneNumber;

    private String email;
    private Address address;

    private GroupOf group;
    private String explanation;

    public Person(int id, String name, PhoneNumber phoneNumber, String email, Address address, GroupOf group, String explanation) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email=email;
        this.address = address;
        this.group = group;
        this.explanation = explanation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GroupOf getGroup() {
        return group;
    }

    public void setGroup(GroupOf group) {
        this.group = group;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", group=" + group +
                ", explanation='" + explanation + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person person) {
        if(this.id > person.id)return 1;
        if(this.id < person.id)return -1;
        return 0;
        //return Integer.compare(this.id, person.id);
    }
}





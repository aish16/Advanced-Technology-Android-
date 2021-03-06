package com.example.aishwarya.email;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aishwarya on 4/9/2017.
 */

public class PrintLetterBarcodeData {
    private static String uid;
    private static String co;
    private static String name;
    private static String street;
    private static String pc;
    private static String yob;
    private static String state;
    private static String gender;
    private static String loc;
    private static String house;
    private static String vtc;
    private static String dist;
    public Map<String, Boolean> stars = new HashMap<>();


    public static String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    @Override
    public String toString() {
        return "uid = " + uid + ", co = " + co + ", name = " + name + ", street = " + street + ", pc = " + pc + ", yob = " + yob + ", state = " + state + ", gender = " + gender + ", loc = " + loc + ", house = " + house + ", vtc = " + vtc + ", dist = " + dist + ".";
    }

    @Exclude
    static Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("co", co);
        result.put("name", name);
        result.put("pc",pc);
        result.put("yob", yob);
        result.put("state", state);
        result.put("gender", gender);
        result.put("loc", loc);
        result.put("house", house);
        result.put("vtc", vtc);
        result.put("dist", dist);

        return result;
    }
}

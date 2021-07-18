package au.edu.jcu.flatOnRent.onrent.list;

public class ModelClassPg {

    String accomodation, address, furnished, location, meat, rent, room, security_fee, time, water,about,pg_for;

    public ModelClassPg(String accomodation, String address, String furnished, String location, String meat, String rent, String room, String security_fee, String time, String water) {
        this.accomodation = accomodation;
        this.address = address;
        this.furnished = furnished;
        this.location = location;
        this.meat = meat;
        this.rent = rent;
        this.room = room;
        this.security_fee = security_fee;
        this.time = time;
        this.water = water;
        this.about=about;
        this.pg_for=pg_for;
    }

    public ModelClassPg() {

    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFurnished() {
        return furnished;
    }

    public void setFurnished(String furnished) {
        this.furnished = furnished;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSecurity_fee() {
        return security_fee;
    }

    public void setSecurity_fee(String security_fee) {
        this.security_fee = security_fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    public String getPg_for() {
        return pg_for;
    }

    public void setPg_for(String about) {
        this.pg_for = pg_for;
    }
}

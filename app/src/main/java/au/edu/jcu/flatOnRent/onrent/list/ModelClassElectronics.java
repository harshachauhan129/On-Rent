package au.edu.jcu.flatOnRent.onrent.list;

public class ModelClassElectronics {
  String  about,address,category,company,imagee,location,model,number,rent,security;

    public ModelClassElectronics(String about, String address, String category, String company, String imagee, String location, String model, String number, String rent, String security) {
        this.about = about;
        this.address = address;
        this.category = category;
        this.company = company;
        this.imagee = imagee;
        this.location = location;
        this.model = model;
        this.number = number;
        this.rent = rent;
        this.security = security;
    }
    public ModelClassElectronics(){}

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImagee() {
        return imagee;
    }

    public void setImagee(String imagee) {
        this.imagee = imagee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }
}

package au.edu.jcu.flatOnRent.onrent.list;

public class ModelClassBooks
{
    String book_about,book_address,book_author,book_category,book_name,book_rent,book_security,location,image;

    //alt+fn+insert for creatinng model class


    public String getBook_about() {
        return book_about;
    }

    public void setBook_about(String book_about) {
        this.book_about = book_about;
    }

    public String getBook_address() {
        return book_address;
    }

    public void setBook_address(String book_address) {
        this.book_address = book_address;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_category() {
        return book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_rent() {
        return book_rent;
    }

    public void setBook_rent(String book_rent) {
        this.book_rent = book_rent;
    }

    public String getBook_security() {
        return book_security;
    }

    public void setBook_security(String book_security) {
        this.book_security = book_security;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ModelClassBooks(String book_about, String book_address, String book_author, String book_category, String book_name, String book_rent, String book_security, String location, String image) {
        this.book_about = book_about;
        this.book_address = book_address;
        this.book_author = book_author;
        this.book_category = book_category;
        this.book_name = book_name;
        this.book_rent = book_rent;
        this.book_security = book_security;
        this.location = location;
        this.image = image;
    }
    public ModelClassBooks(){}
}

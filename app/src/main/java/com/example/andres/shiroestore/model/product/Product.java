package com.example.andres.shiroestore.model.product;


public class Product {
    private String id;
    private String name;
    private int category;
    private int price;
    private int cant;
    private String photo;
    private String detail;

    public Product(String id, String name, int category, int price, int cant, String photo, String detail) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.cant = cant;
        this.photo = photo;
        this.detail = detail;
    }

    public Product() {

    }

    public Product(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void save() {
        ProductData.addProduct(this);
    }

    public void delete(){
        ProductData.deleteProduct(this);
    }

    public void edit() {
        ProductData.editProdduct(this);
    }
}

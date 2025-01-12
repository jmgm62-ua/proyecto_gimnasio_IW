package iwebgym.dto;

public class StringIntTuple {
    private String nombre;
    private double price;

    public StringIntTuple(String nombre, double price) {
        this.nombre = nombre;
        this.price = price;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrice() {
        return price;
    }
}

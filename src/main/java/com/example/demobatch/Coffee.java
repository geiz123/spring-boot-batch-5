package com.example.demobatch;

public class Coffee {

	private Integer coffee_id;
    private String brand;
    private String origin;
    private String characteristics;

    public Coffee() {
    	
    }
    
    public Coffee(Integer coffee_id, String brand, String origin, String characteristics) {
    	this.setCoffee_id(coffee_id);
        this.brand = brand;
        this.origin = origin;
        this.characteristics = characteristics;
    }

	public String getBrand() {
		return brand;
	}

	public String getOrigin() {
		return origin;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	@Override
	public String toString() {
		return "Coffee [coffee_id=" + coffee_id + ", brand=" + brand + ", origin=" + origin + ", characteristics="
				+ characteristics + "]";
	}

	public Integer getCoffee_id() {
		return coffee_id;
	}

	public void setCoffee_id(Integer coffee_id) {
		this.coffee_id = coffee_id;
	}

}
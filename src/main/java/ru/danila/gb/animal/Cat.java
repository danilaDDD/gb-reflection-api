package ru.danila.gb.animal;

public class Cat extends Animal{
    private int countLegs;

    public Cat(String name, int age, int countLegs) {
        super(name, age);
        this.countLegs = countLegs;
    }

    public int getCountLegs() {
        return countLegs;
    }

    public void makeSound(){
        System.out.println("say may");
    }
}

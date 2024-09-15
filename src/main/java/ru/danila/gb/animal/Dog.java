package ru.danila.gb.animal;

public class Dog extends Animal{
    private boolean existTail;

    public Dog(String name, int age, boolean existTail) {
        super(name, age);
        this.existTail = existTail;
    }

    public void sayGah(){
        System.out.println("gaph");
    }

    public boolean isExistTail() {
        return existTail;
    }

    public void makeSound(){
        sayGah();
    }
}

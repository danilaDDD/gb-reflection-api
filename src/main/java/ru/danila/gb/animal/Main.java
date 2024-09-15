package ru.danila.gb.animal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
Создайте абстрактный класс Animal c полями name и age
Реализуйте наследников Dog и Cat со спецыфичными полями иметодами
Создайте массив объектов Animal и выведети всю информацию о каждом объекте и вызовите метод makeSound если он есть
 */

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Animal[] animals = {
            new Cat("cat1", 1, 4),
            new Dog("dog1", 10, true),
            new Animal("animal", 2){
                public void makeSound(){
                    System.out.println("sound");
                }
            },
            new Cat("cat2", 4, 8)
        };

        for(Animal animal: animals){
            printObjectInfo(animal);
        }
    }

    private static void printObjectInfo(Object obj) throws IllegalAccessException {
        Class<?> baseClazz = obj.getClass();
        List<Class<?>> classes = getClasses(baseClazz);

        Map<Field, String> fieldToValueMap = new HashMap<>();
        Set<Method> methodSet = new HashSet<>();

        for(Class<?> clazz: classes) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                fieldToValueMap.put(field, field.get(obj).toString());
            }

            methodSet.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        }

        System.out.println(baseClazz);

        try {
            Method makeSoundMethod = baseClazz.getDeclaredMethod("makeSound");
            makeSoundMethod.invoke(obj);
        } catch (NoSuchMethodException e) {}
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }


        if(!fieldToValueMap.isEmpty()){
            System.out.println("fields:");
            fieldToValueMap.forEach((key, value) -> System.out.printf("%s=%s%n", key, value));
        }

        if(!methodSet.isEmpty()){
            System.out.println("methods:");
            methodSet.forEach(System.out::println);
        }
    }

    private static List<Class<?>> getClasses(Class<?> baseClazz) {
        List<Class<?>> classes = new ArrayList<>(List.of(baseClazz));
        Class<?> superClass = baseClazz.getSuperclass();
        while (!superClass.equals(Object.class)){
            classes.add(superClass);
            superClass = superClass.getSuperclass();
        }

        return classes;
    }


}

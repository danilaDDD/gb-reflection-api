package ru.danila.gb.querybuilder;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Employee employee1 = new Employee("danila", "danila@yandex.ru");

        QueryBuilder queryBuilder = new QueryBuilder();
        System.out.println(queryBuilder.buildInsertQuery(employee1));
        System.out.println(queryBuilder.buildSelectQuery(employee1.getClass(), employee1.getId()));
        System.out.println(queryBuilder.buildDeleteQuery(employee1.getClass(), employee1.getId()));

    }
}
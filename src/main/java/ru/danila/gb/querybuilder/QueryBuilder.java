package ru.danila.gb.querybuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QueryBuilder {
    public String buildInsertQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder query = new StringBuilder("INSERT INTO ");

        if(clazz.isAnnotationPresent(Table.class)){
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query
                    .append(tableAnnotation.name());
            Field[] fields = clazz.getDeclaredFields();
            List<String> fieldNames = new ArrayList<>(fields.length);
            List<String> fieldValues = new ArrayList<>(fields.length);

            for(Field field: fields){
                if(field.isAnnotationPresent(Column.class)){
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    fieldNames.add(columnAnnotation.name());
                    fieldValues.add(field.get(obj).toString());
                }
            }

            query.append(String.format("(%s)", String.join(", ", fieldNames)));
            query.append(String.format(" VALUES (%s)", String.join(", ", fieldValues)));

            return query.toString();
        }
        else{
            return null;
        }
    }

    public String buildSelectQuery(Class<?> clazz, UUID primaryKey){
        if(clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            String tableName = table.name();
            String primaryKeyName = "";

            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields){
                Column columnAnnotation = field.getAnnotation(Column.class);
                if(columnAnnotation.primaryKey()){
                    primaryKeyName = columnAnnotation.name();
                }
            }

            return String.format("SELECT * FROM %s WHERE %s=%s", tableName, primaryKeyName, primaryKey);
        }else{
            return null;
        }
    }

    public String buildDeleteQuery(Class<?> clazz, UUID primaryKey){
        if(!clazz.isAnnotationPresent(Table.class))
            return null;

        String tableName = clazz.getAnnotation(Table.class).name();

        List<Field> fieldList = List.of(clazz.getDeclaredFields());
        fieldList.forEach(field -> field.setAccessible(true));
        String primaryKeyLabel = fieldList.stream()
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class))
                .filter(Column::primaryKey)
                .map(Column::name)
                .findFirst().orElse(null);

        if(primaryKeyLabel == null)
            return null;

        return String.format("DELETE FROM %s WHERE %s=%s", tableName, primaryKeyLabel, primaryKey);

    }
}

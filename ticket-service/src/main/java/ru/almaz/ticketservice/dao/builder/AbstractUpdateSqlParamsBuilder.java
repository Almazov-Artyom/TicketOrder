package ru.almaz.ticketservice.dao.builder;

import lombok.SneakyThrows;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.dto.UpdateCarrierDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractUpdateSqlParamsBuilder<T> implements SqlParamsBuilder<T> {
    private final Map<String, Field> fieldToColumn;

    private final Class<T> clazz;

    private void cacheFields(){
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(ColumnMapping.class)){
                String value = field.getAnnotation(ColumnMapping.class).value();
                fieldToColumn.put(value, field);
            }
            else
                fieldToColumn.put(field.getName(), field);
        }
    }

    protected AbstractUpdateSqlParamsBuilder(Class<T> clazz) {
        fieldToColumn = new HashMap<>();
        this.clazz = clazz;
        cacheFields();
    }

    protected abstract String getTableName();

    @Override
    @SneakyThrows
    public Map.Entry<String, List<Object>> buildSqlAndParams(T t) {
        StringBuilder sql = new StringBuilder("UPDATE ")
                .append(getTableName())
                .append(" SET ");
        List<Object> params = new ArrayList<>();

        Class<UpdateCarrierDto> updateCarrierDtoClass = UpdateCarrierDto.class;

        boolean first = true;
        for (var entry : fieldToColumn.entrySet()){
            Object value = entry.getValue().get(t);
            if(value != null){
                String columnName = entry.getKey();

                if(!first) sql.append(",");
                first = false;

                sql.append(columnName).append(" = ?");
                params.add(value);
            }
        }

        return Map.entry(sql.toString(), params);
    }
}

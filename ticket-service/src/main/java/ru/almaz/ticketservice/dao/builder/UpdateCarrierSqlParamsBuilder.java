package ru.almaz.ticketservice.dao.builder;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.dto.UpdateCarrierDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateCarrierSqlParamsBuilder implements SqlParamsBuilder<UpdateCarrierDto> {

    private final Map<String,String> fieldToColumn;

    private void cacheFields(){
        Class<UpdateCarrierDto> updateCarrierDtoClass = UpdateCarrierDto.class;
        Field[] declaredFields = updateCarrierDtoClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(ColumnMapping.class)){
                String value = field.getAnnotation(ColumnMapping.class).value();
                fieldToColumn.put(field.getName(),value);
            }
        }
    }

    public UpdateCarrierSqlParamsBuilder() {
        fieldToColumn = new HashMap<>();
        cacheFields();
        buildSqlAndParams(new UpdateCarrierDto(null,null));
    }

    @Override
    @SneakyThrows
    public Map.Entry<String, List<Object>> buildSqlAndParams(UpdateCarrierDto updateCarrierDto) {
        StringBuilder sql = new StringBuilder("UPDATE carrier");
        List<Object> params = new ArrayList<>();

        Class<UpdateCarrierDto> updateCarrierDtoClass = UpdateCarrierDto.class;
        Field[] declaredFields = updateCarrierDtoClass.getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(updateCarrierDto);
            if(value != null){
                String columnName = fieldToColumn.get(field.getName());
                if(columnName == null) continue;

                sql.append(" SET ").append(columnName).append(" = ?");
                params.add(value);
            }
        }

        return Map.entry(sql.toString(), params);
    }
}

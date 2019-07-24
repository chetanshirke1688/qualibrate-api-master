package com.qualibrate.api.commons.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.hibernate.proxy.HibernateProxy;

import com.qualibrate.api.exceptions.ErrorCodes;
import com.qualibrate.api.exceptions.UnSupportedFormatException;

import lombok.extern.slf4j.Slf4j;

/**
 * Entity to DTO mapper is used to transform entity classes to Data objects.
 * Generic template is created to generalize transformation across application
 * entities.
 * @author <a href="mailto:chetan.shirke1688@gmail.com">chetan shirke</a>
 *
 * @param <S> Source Entity
 * @param <T> Data Object
 */
@Slf4j
public class EntityToDtoTransformer<E extends Entity, D extends Dto> implements ObjectTransformer<E, D> {

    private D dtoOject;

    public EntityToDtoTransformer(D dto) {
        this.dtoOject = dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    public D apply(E entity) {
        try {
            D dtoObject = (D) this.dtoOject.getClass().getDeclaredConstructor().newInstance();
            copyProperties(entity, dtoObject);
            return dtoObject;
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InstantiationException
            | InvocationTargetException | NoSuchMethodException e) {
            throw new UnSupportedFormatException(e, "Failed to convert Entity object to DTO.",
            ErrorCodes.FORMAT_NOT_SUPPORTED);
        }
    }

    /**
     * Copy properties from entity to dto
     *
     * @param e Entity
     * @param d Dto
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    @SuppressWarnings("unchecked")
    private void copyProperties(E e, D d) throws SecurityException, IllegalArgumentException, IllegalAccessException {
        Field[] destinationFields = d.getClass().getDeclaredFields();
        Arrays.stream(destinationFields).map(destinationField -> {
                try {
                    if (!destinationField.isAccessible()) {
                        destinationField.setAccessible(true);
                    }
                    E copy = e;
                    if (copy instanceof HibernateProxy) {
                        copy = (E) ((HibernateProxy) copy).getHibernateLazyInitializer().getImplementation();
                    }
                    Field sourceField = copy.getClass().getDeclaredField(destinationField.getName());
                    if (sourceField != null) {
                        if (!sourceField.isAccessible()) {
                            sourceField.setAccessible(true);
                        }
                        destinationField.set(d, sourceField.get(copy));
                    }
                } catch (IllegalAccessException | NoSuchFieldException ex) {
                    log.warn("Skip property. since it does not exist in source object");
                }
                return d;
            }).collect(Collectors.toList());
    }
}

package com.pizza.util.converter;

import com.pizza.dto.PizzaDto;
import com.pizza.model.Pizza;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.Collection;
import java.util.Optional;

@Mapper(nullValueMappingStrategy=NullValueMappingStrategy.RETURN_DEFAULT, uses={IngredientConverter.class})
public interface PizzaConverter {

    /**
     * Create a new {@link Pizza} which properties match with the given {@link PizzaDto}
     *
     * @param pizzaDto
     *    {@link PizzaDto} with the "source information"
     *
     * @return {@link Pizza}
     */
    Pizza fromDtoToEntity(PizzaDto pizzaDto);

    /**
     * Create a new {@link Pizza} which properties match with the given {@link PizzaDto}
     *
     * @param pizzaDto
     *    {@link PizzaDto} with the "source information"
     *
     * @return {@link Optional} of {@link Pizza}
     */
    default Optional<Pizza> fromDtoToOptionalEntity(PizzaDto pizzaDto) {
        return Optional.ofNullable(pizzaDto)
                       .map(this::fromDtoToEntity);
    }

    /**
     *    Return a new {@link Collection} of {@link Pizza} with the information contains in the given
     * {@link Collection} of {@link PizzaDto}
     *
     * @param pizzaDtos
     *    {@link Collection} of {@link PizzaDto} with the "source information"
     *
     * @return {@link Collection} of {@link Pizza}
     */
    Collection<Pizza> fromDtosToEntities(Collection<PizzaDto> pizzaDtos);

    /**
     * Create a new {@link PizzaDto} which properties match with the given {@link Pizza}
     *
     * @param pizza
     *    {@link Pizza} with the "source information"
     *
     * @return {@link PizzaDto}
     */
    PizzaDto fromEntityToDto(Pizza pizza);

    /**
     * Create a new {@link PizzaDto} which properties match with the given {@link Pizza}
     *
     * @param pizza
     *    {@link Pizza} with the "source information"
     *
     * @return {@link Optional} of {@link PizzaDto}
     */
    default Optional<PizzaDto> fromEntityToOptionalDto(Pizza pizza) {
        return Optional.ofNullable(pizza)
                       .map(this::fromEntityToDto);
    }

    /**
     *    Return a new {@link Collection} of {@link PizzaDto} with the information contains in the given
     * {@link Collection} of {@link Pizza}
     *
     * @param pizzas
     *    {@link Collection} of {@link Pizza} with the "source information"
     *
     * @return {@link Collection} of {@link PizzaDto}
     */
    Collection<PizzaDto> fromEntitiesToDtos(Collection<Pizza> pizzas);

}
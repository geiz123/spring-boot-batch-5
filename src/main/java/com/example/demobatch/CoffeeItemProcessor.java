package com.example.demobatch;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CoffeeItemProcessor implements ItemProcessor<ABean, Coffee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeItemProcessor.class);

    private static final String EXTRA = "@#";
    
    @Override
    public Coffee process(final @NonNull ABean bean) throws InterruptedException {
    	Integer coffee_id = bean.getCoffee_id();
        String brand = bean.getBrand().toUpperCase() + EXTRA;
        String origin = bean.getOrigin().toUpperCase() + EXTRA;
        String chracteristics = bean.getCharacteristics().toUpperCase() + EXTRA;

        Coffee transformedCoffee = new Coffee(coffee_id, brand, origin, chracteristics);
        LOGGER.info("Converting ( {} ) into ( {} )", bean, transformedCoffee);
        
        Random ran = new Random();
        Integer time = ran.nextInt(5, 30000);
        
        LOGGER.info("{} @@@ Sleeping for {}", time, time);
        
        Thread.sleep(time);
        
        LOGGER.info("{} @@@ Wokeup!", time);

        return transformedCoffee;
    }

}

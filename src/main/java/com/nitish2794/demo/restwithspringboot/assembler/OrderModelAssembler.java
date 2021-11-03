package com.nitish2794.demo.restwithspringboot.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.nitish2794.demo.restwithspringboot.controller.rmmlevel3.Level3OrderController;
import com.nitish2794.demo.restwithspringboot.entity.Order;
import com.nitish2794.demo.restwithspringboot.enums.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(Level3OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(Level3OrderController.class).all()).withRel("orders"));

        // Conditional links based on state of the order

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(Level3OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(Level3OrderController.class).complete(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}

package com.cityescape.web.assemblers;

import com.cityescape.domain.TripTag;
import com.cityescape.web.resource.TripTagResource;
import com.cityescape.web.controller.TripTagController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by Slava on 18/02/2016.
 */
@Component
public class TripTagResourceAssembler extends IdentifiableResourceAssemblerSupport<TripTag, TripTagResource> {

    public TripTagResourceAssembler() {
        super(TripTagController.class, TripTagResource.class);
    }

    @Override
    public TripTagResource toResource(TripTag tripTag) {

        TripTagResource resource = new TripTagResource();
        resource.setTagId(tripTag.getId());
        resource.setTag(tripTag.getTag());
        resource.setDescription(tripTag.getDescription());

        resource.add((ControllerLinkBuilder.linkTo((ControllerLinkBuilder.methodOn(TripTagController.class)
                .getTripTagByTag(tripTag.getTag()))).withSelfRel()));

        return resource;
    }

    public void addLinksToResource(TripTagResource resource) {

        resource.add(ControllerLinkBuilder
                .linkTo(TripTagController.class)
                .withRel("triptags"));

//        resource.add(ControllerLinkBuilder
//                .linkTo(ControllerLinkBuilder
//                        .methodOn(TripTagController.class)
//                        .getUpdateBonusOfferTagForm(bonusOfferTagResource.getTagId()))
//                .withRel(ApplicationProtocol.UPDATE_BONUSOFFER_TAG_FORM_REL));
//
//        resource.add(ControllerLinkBuilder
//                .linkTo(ControllerLinkBuilder
//                        .methodOn(TripTagController.class)
//                        .deleteBonusOfferTag(bonusOfferTagResource.getTagId()))
//                .withRel(ApplicationProtocol.DELETE_BONUSOFFER_TAG_ACTION_REL));
    }
}

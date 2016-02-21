package com.cityescape.web.assemblers;

import com.cityescape.domain.TripTag;
import com.cityescape.web.form.TripTagForm;
import com.cityescape.web.resource.TripTagResource;
import com.cityescape.web.controller.TripTagController;
import com.cityescape.web.resource.TripTagResourceCollection;
import com.cityescape.web.support.NavigationLinkBuilder;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cityescape.web.support.NavigationLinkBuilder.linkToCurrentRequest;

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

        resource.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripTagController.class)
                        .deleteTripTagByTag(resource.getTag()))
                .withRel("delete-trip-tag-action"));
    }

    public void addLinksToCollection(TripTagResourceCollection collection) {

        collection.setTotalItems(collection.getContent().size());

        collection.add(NavigationLinkBuilder.linkToCurrentRequest().withSelfRel());
        collection.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripTagController.class)
                        .getCreateTripTagForm())
                .withRel("trip-tag-form"));
    }

    public void addLinksToForm(TripTagForm form, String poeTag) {
        form.add(linkToCurrentRequest().withSelfRel());

        form.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripTagController.class)
                        .createTripTag(poeTag, form))
                .withRel("create-trip-tag-action"));
    }

    public TripTagResourceCollection toResourceCollection(List<TripTag> tripTags) {

        List<TripTagResource> tripTagResources = new ArrayList<>();

        tripTagResources.addAll(tripTags.stream().map(this::toResource).collect(Collectors.toList()));

        TripTagResourceCollection collection = new TripTagResourceCollection(tripTagResources);
        addLinksToCollection(collection);

        return collection;
    }
}

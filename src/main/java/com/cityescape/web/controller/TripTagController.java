package com.cityescape.web.controller;

import com.cityescape.domain.PoeTag;
import com.cityescape.domain.TripTag;
import com.cityescape.service.PoeTagService;
import com.cityescape.web.form.TripTagForm;
import com.cityescape.web.resource.TripTagResource;
import com.cityescape.service.TripTagService;
import com.cityescape.web.assemblers.TripTagResourceAssembler;
import com.cityescape.web.resource.TripTagResourceCollection;
import com.cityescape.web.support.TripTagTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Created by Slava on 15/02/2016.
 */
@RestController
@RequestMapping(value = "triptags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripTagController extends AbstractController {

    @Autowired
    private TripTagService tripTagService;

    @Autowired
    TripTagResourceAssembler tripTagResourceAssembler;

    @Autowired
    private PoeTagService poeTagService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<TripTagResourceCollection> getTripTags() {
        List<TripTag> tripTags = tripTagService.findAll();
        TripTagResourceCollection resources = tripTagResourceAssembler.toResourceCollection(tripTags);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{tripTag}", method = RequestMethod.GET)
    public HttpEntity<TripTagResource> getTripTagByTag(@PathVariable("tripTag") String tripTagName) {

        TripTag tripTag = tripTagService.findByTag(tripTagName);
        TripTagResource resource = tripTagResourceAssembler.toResource(tripTag);

        tripTagResourceAssembler.addLinksToResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{tripTag}", method = RequestMethod.DELETE)
    public HttpEntity<TripTagResource> deleteTripTagByTag(@PathVariable("tripTag") String tripTagName) {

        TripTag tripTag = tripTagService.findByTag(tripTagName);
        tripTagService.delete(tripTag);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public HttpEntity<TripTagForm> getCreateTripTagForm() {
        TripTagForm form = new TripTagForm();

        String poeTag = poeTagService.createTag(ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
        tripTagResourceAssembler.addLinksToForm(form, poeTag);
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/create/{poeTag}", method = RequestMethod.POST)
    public HttpEntity<TripTagResource> createTripTag(@PathVariable("poeTag") String poeTag, @Valid @RequestBody TripTagForm form) {

        PoeTag poeTagRetrieved = poeTagService.getTag(poeTag);
        if (poeTagRetrieved == null || poeTagRetrieved.getConsumed()) {
            return methodNotAllowed();
        }

        poeTagService.consumeTag(poeTag);
        TripTag tripTag = tripTagService.create(TripTagTransformer.transformToTripTag(form));
        TripTagResource tripTagResource = tripTagResourceAssembler.toResource(tripTag);

        return new ResponseEntity<>(tripTagResource, HttpStatus.OK);
    }
}

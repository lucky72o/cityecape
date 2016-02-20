package com.cityescape.web.support;

import com.cityescape.domain.TripTag;
import com.cityescape.web.form.TripTagForm;

/**
 * Created by Slava on 20/02/2016.
 */
public class TripTagTransformer {
    public static TripTag transformToTripTag(TripTagForm form) {
        TripTag tripTag = new TripTag(form.getTag(), form.getDescription());
        return tripTag;
    }
}

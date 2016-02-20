package com.cityescape.web.support;


import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.LinkBuilderSupport;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper for adding page navigation links to a resource representation.
 *
 */
public class NavigationLinkBuilder extends LinkBuilderSupport<NavigationLinkBuilder> {

    public static NavigationLinkBuilder linkToCurrentRequestUri() {
        return new NavigationLinkBuilder(ServletUriComponentsBuilder.fromCurrentRequestUri());
    }

    public static NavigationLinkBuilder linkToCurrentRequest() {
        return new NavigationLinkBuilder(ServletUriComponentsBuilder.fromCurrentRequest());
    }

    public static NavigationLinkBuilder linkToCurrentRequest(String pageName, String pageSizeName, int page,
                                                             int pageSize) {
        return new NavigationLinkBuilder(ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam(pageName, page)
                .replaceQueryParam(pageSizeName, pageSize)
        );
    }

    public static void addNavigationLinks(ResourceSupport resource, Page page) {
        String pageName = "page";

        String pageSizeName = "page_size";

        resource.add(linkToCurrentRequest().withSelfRel());

        if (!page.isFirst()) {
            resource.add(NavigationLinkBuilder
                    .linkToCurrentRequest(pageName, pageSizeName, 0, page.getSize())
                    .withRel(Link.REL_FIRST));
        }

        if (!page.isLast() && page.hasContent()) {
            resource.add(NavigationLinkBuilder
                    .linkToCurrentRequest(pageName, pageSizeName, page.getTotalPages() - 1, page.getSize())
                    .withRel(Link.REL_LAST));
        }

        if (page.hasPrevious()) {
            resource.add(NavigationLinkBuilder
                    .linkToCurrentRequest(pageName, pageSizeName, page.getNumber() - 1, page.getSize())
                    .withRel(Link.REL_PREVIOUS));
        }

        if (page.hasNext()) {
            resource.add(NavigationLinkBuilder
                    .linkToCurrentRequest(pageName, pageSizeName, page.getNumber() + 1, page.getSize())
                    .withRel(Link.REL_NEXT));
        }
    }

    private NavigationLinkBuilder(UriComponentsBuilder builder) {
        super(builder);
    }

    @Override
    protected NavigationLinkBuilder createNewInstance(UriComponentsBuilder builder) {
        return new NavigationLinkBuilder(builder);
    }

    @Override
    protected NavigationLinkBuilder getThis() {
        return this;
    }


    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }
}

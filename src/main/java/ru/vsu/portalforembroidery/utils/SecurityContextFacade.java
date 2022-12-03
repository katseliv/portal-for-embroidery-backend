package ru.vsu.portalforembroidery.utils;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextFacade {

    SecurityContext getContext();

}

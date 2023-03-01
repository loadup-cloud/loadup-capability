package com.github.loadup.capability.common.model;

import com.github.loadup.capability.common.util.ToStringUtils;

import java.io.Serializable;

/**
 * Data Transfer object, including Command, Query and Response,
 *
 * Command and Query is CQRS concept.
 */
public abstract class DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringUtils.toString(this);
    }
}

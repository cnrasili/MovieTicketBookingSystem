package com.cnrasili.moviebooking.model;

/**
 * Defines the age restriction levels for movies.
 * Used to validate if a customer is eligible to watch a specific movie.
 *
 * @author cnrasili
 * @version 1.0
 */
public enum AgeRating {
    /** Suitable for all ages. */
    GENERAL_AUDIENCE,

    /** Suitable for viewers aged 7 and above. */
    PLUS_7,

    /** Suitable for viewers aged 13 and above. */
    PLUS_13,

    /** Strictly for viewers aged 18 and above. */
    PLUS_18
}

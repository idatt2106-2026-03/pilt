package ntnu.idi.idatt2106.pilt.features.badge.model;

/**
 * Enum representing how a badge can be earned.
 * This determines the awarding logic in the service layer.
 */
public enum BadgeType {

    /**
     * Awarded automatically when a student completes all tasks at a stoppested.
     * One badge per stoppested.
     */
    STOPPESTED_COMPLETION,

    /**
     * Awarded when a student's "ukens mysterium" submission is approved by a teacher.
     */
    WEEKLY_MYSTERY,

    /**
     * Awarded for beating the final boss at Datasenteret.
     * Distinct from STOPPESTED_COMPLETION.
     */
    FINAL_BOSS,

    /**
     * Reserved for special achievements.
     */
    SPECIAL
}

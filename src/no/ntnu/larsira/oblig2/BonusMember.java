package no.ntnu.larsira.oblig2;

import java.time.LocalDate;
import java.time.Period;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * The bonus-member class represents a bonus-member. The bonus-member has
 * a number, personals for a member (stored as a Personals object), as well as points
 * and time of registration. As personals are to be stored separately, only points and
 * registration-time are stored directly on the BonusMember.
 */

public class BonusMember {
    /**
     * Constants used for scaling the amount of points given to bonus-members
     */
    protected final static double FACTOR_SILVER = 1.20;
    protected final static double FACTOR_GOLD = 1.50;
    
    private final int memberNo;
    private final Personals personals;
    private final LocalDate enrolledDate;
    private int points = 0;
    
    /**
     * Creates an instance of BonusMember
     * @param memberNo the members member number, must be unique
     * @param personals Personals object, containing the personals of the member
     * @param enrolledDate The date the member enrolled
     */
    public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate) {
        this.memberNo = memberNo;
        this.personals = personals;
        this.enrolledDate = enrolledDate;
    }
    
    /**
     * Returns member number
     * @return member number
     */
    public int getMemberNo() {
        return memberNo;
    }
    
    /**
     * Returns personals-object
     * @return personals-object
     */
    public Personals getPersonals() {
        return personals;
    }
    
    /**
     * Returns the day of enrollment for member
     * @return day of enrollment for member
     */
    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }
    
    /**
     * Returns the current points of the member
     * @return the current points of the member
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Gets points eligible for upgrading a member.
     * @param currentDate
     * @return amount of points eligible for upgrade
     */
    public int findQualificationPoints( LocalDate currentDate ) {
        int eligiblePoints = 0;
        Period p = Period.between(enrolledDate, currentDate);
        long daysBetween = DAYS.between(enrolledDate, currentDate);

        if( daysBetween < 365 ) {
            eligiblePoints = this.points;
        }
        
        return eligiblePoints;
    }

    /**
     * Confirms that a given password is the one matching the member.
     * returns <code>true</code> if the password matches the one belonging to the
     * personalia, or <code>false</code> if it does not
     * @param password password to check
     * @return true if the provided password matches the stored one, otherwise
     * false is returned
     */
    public boolean okPassword(String password) {
        return personals.okPassword(password);
    }

    /**
     * Registers a specified amount of points
     * @param points points to register
     */
    public void registerPoints(int points) {
        this.points += points;
    }
}

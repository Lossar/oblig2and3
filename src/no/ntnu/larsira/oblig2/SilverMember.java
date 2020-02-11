package no.ntnu.larsira.oblig2;

import java.time.LocalDate;

public class SilverMember extends BonusMember {

    public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate, int points) {
        super(memberNo, personals, enrolledDate);
        super.registerPoints(points);
    }

    /**
     * Takes a sum of points, scales it with appropriate multiplier, and adds
     * the points to the member.
     * @param points points to register
     */
    @Override
    public void registerPoints(int points) {
        super.registerPoints((int)(points * FACTOR_SILVER));
    }
}

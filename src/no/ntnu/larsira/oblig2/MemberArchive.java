package no.ntnu.larsira.oblig2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MemberArchive {
    /**
     * Values for points required for a member to be
     * upgraded.
     */
    protected static final int SILVER_LIMIT = 25000;
    protected static final int GOLD_LIMIT = 75000;
    private static final int MAX_TRY = 2;
    private static final Random RANDOM_NUMBER = new Random();
    private final HashMap<Integer, BonusMember> members;
    private final LocalDate currentDate;
    
    public static void main(String args[]) {
        //TODO, add some fun GUI
    }
    
    /**
     * Constructor for MemberArchive.
     */
    public MemberArchive(LocalDate currentDate){
        members = new HashMap<Integer, BonusMember>();
        this.currentDate = currentDate;
        System.out.println("Member archive constructed");
    }
    
    /**
     * Returns the amount of points belonging to a member matching the provided member
     * number. If the member number is invalid or the password is wrong, a negative value
     * (-1) is returned
     * @param memberNo membernumber of the member whose points are to be fetched
     * @param password password of the member
     * @return the amount of points belonging to the member if their number and password
     * are correct, otherwise -1 is returned
     */
    public int findPoints(int memberNo, String password) {
        int points = -1;
        BonusMember toFind = members.get(memberNo);
        if(toFind != null && toFind.okPassword(password)) {
            points = toFind.getPoints();
        }
        return points;
    }
    
    /**
     * Adds a member with the provided personalia, and assigns the provided <code>LocalDate</code> as
     * the member's date of registration
     * @param pers Personalia object for the new member.
     * @param dateEnrolled LocalDate representing the date the member enrolled
     * @return member number of the newly added member
     */
    public int addMember(Personals pers, LocalDate dateEnrolled) {
        int memberNumber = findAvailableNo();
        if(Period.between(dateEnrolled,currentDate).isNegative()) {
            System.out.println("You may not register members from the future!");
            dateEnrolled = currentDate;
        }
        BasicMember newMember = new BasicMember(memberNumber, pers, dateEnrolled);
        members.put(memberNumber, newMember);
        
        return memberNumber;
    }
    
    /**
     * Generates a random number that is not already used as a member number.
     * @return a random integer number, not equal to any existing member numbers
     */
    private int findAvailableNo() {
        int availableNo = RANDOM_NUMBER.nextInt();
        while(members.containsKey(availableNo)) {
            availableNo = RANDOM_NUMBER.nextInt();
        }
        return availableNo;
    }
    
    /**
     * Returns the amount of members in the registry
     * @return amount of members in the registry
     */
    public int amountOfMembers() {
        return members.values().size();
    }
    
    /**
     * Adds points to the member matching the member-number. Returns true if the member with the number exists, or
     * false otherwise
     */
    public boolean registerPoints(int memberNo, int pointsToAdd) {
        
        boolean success = false;
        BonusMember memberToGivePoints = members.get(memberNo);
        
        if(memberToGivePoints != null) {
            memberToGivePoints.registerPoints(pointsToAdd);
            success = true;
        }
        return success;
    }
    /**
     * Checks every registered member and upgrades their membership when applicable.
     */
    public void checkMembers() {
        members.values().stream()
                .forEach(member -> {
                            BonusMember upgradedMember = null;
                            
                            if (eligibleForGold(member)) {
                                upgradedMember = new GoldMember(member.getMemberNo(),
                                        member.getPersonals(), member.getEnrolledDate(), member.getPoints());
                                
                            } else if (eligibleForSilver(member)) {
                                upgradedMember = new SilverMember(member.getMemberNo(), member.getPersonals(),
                                        member.getEnrolledDate(), member.getPoints());
                            }
                            
                            if(upgradedMember != null){
                                members.put(upgradedMember.getMemberNo(), upgradedMember);
                            }
                        });
    }
    
    /**
     * Checks if a provided member is eligible for gold. The member must have more points than
     * the gold limit, and the member may not already be a gold member
     * @param member member to check for gold-eligibility
     * @return true if member is eligible for gold, false otherwise
     */
    private boolean eligibleForGold(BonusMember member) {
        return !(member instanceof GoldMember) && member.findQualificationPoints(currentDate) >= GOLD_LIMIT;
    }
    
    /**
     * Checks if a provided member is eligible for silver. The member must have more points than the silver-limit, and the
     * member must be a basic member.
     * @param member member to check for silver-eligibility
     * @return true if member is eligible for gold, false otherwise
     */
    private boolean eligibleForSilver(BonusMember member) {
        return (member instanceof BasicMember) && member.findQualificationPoints(currentDate) >= SILVER_LIMIT;
    }
    /**
     * Prints the classes of all members.
     */
    public void printMemberClasses() {
        members.values().forEach(m -> {
                System.out.println("This member, " + m.getMemberNo() + " , with " + m.findQualificationPoints(currentDate) +" has the following class");
                System.out.println(m.getClass());
        });
    }
    /**
     * Checks every registered member and upgrades their membership when applicable.
     */
   // public void checkMembers() {
       // members.values().stream()
        //        .filter(m -> eligibleForGold(m) || eligibleForSilver(m))
         //       .forEach(m -> {
         //           if (eligibleForGold(m)) {
         //               m = new GoldMember(m.getMemberNo(), m.getPersonals(), m.getEnrolledDate(), m.getPoints());
        //            } else if (eligibleForSilver(m)) {
       //                 m = new SilverMember(m.getMemberNo(), m.getPersonals(), m.getEnrolledDate(), m.getPoints());
      //              }
     //               members.put(m.getMemberNo(), m);
    //            });
    //}
}

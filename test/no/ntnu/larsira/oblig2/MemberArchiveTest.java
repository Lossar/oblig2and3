package no.ntnu.larsira.oblig2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberArchiveTest {
    MemberArchive memberArchive = new MemberArchive(LocalDate.of(2008,3,12));
    Personals person00;
    LocalDate date00;
    @BeforeEach
    void setUp() {
        person00 = new Personals("Ola","Nordmann","Ola@Nordmann.com","WordOfPass");
        date00 = LocalDate.of(2008, 2, 10);
    }
    
    @Test
    void findPoints() {
        int memberNo = memberArchive.addMember(person00, date00);
        assertEquals(-1,memberArchive.findPoints(memberNo, "wordfddfdofpass"));
        assertEquals(0, memberArchive.findPoints(memberNo, "wordofpass"));
        memberArchive.registerPoints(memberNo, 5000);
        assertEquals(5000, memberArchive.findPoints(memberNo, "wordofpass"));
    }
    
    @Test
    void addMember() {
        memberArchive.addMember(person00, date00);
        assertEquals(memberArchive.amountOfMembers(), 1);
        person00 = new Personals("somePerson","NiceLastName","mailYes","pasYes");
        date00 = LocalDate.of(2009, 3, 20);
        memberArchive.addMember(person00, date00);
        assertEquals(memberArchive.amountOfMembers(),2);
    }
    
    @Test
    void testCheckMembers() {
        int memberOne = memberArchive.addMember(person00, date00);
        
        person00 = new Personals("somePerson","NiceLastName","mailYes","pasYes");
        date00 = LocalDate.of(2006, 9, 20);
        int memberTwo = memberArchive.addMember(person00,date00);
        
        person00 = new Personals("lastPerson", "NicestLastName", "mailNo", "wordOfpassword");
        date00 = LocalDate.of(2008, 9, 20);
        int memberThree = memberArchive.addMember(person00, date00);
        
        memberArchive.printMemberClasses();
        System.out.println("");
        
        memberArchive.registerPoints(memberOne, 25000);
        memberArchive.registerPoints(memberTwo, 78000);
        memberArchive.registerPoints(memberThree, 1000000);
        memberArchive.checkMembers();
        memberArchive.printMemberClasses();
        
        System.out.println("");
        memberArchive.registerPoints(memberOne, 75000);
        memberArchive.checkMembers();
        memberArchive.printMemberClasses();
    }
}
import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS06 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "date-submitted": "Feb.2, 2020",
    "date-entered": "feb. 3, 2020",
    "diss-title": "THE HISTORY OF A JEDI TURNED SITH",
    "breadth-course-info": ["I", "AM" ,"YOUR" , "FATHER", "...", "NOOOOOOOOOOOOOOO"],
    "breadth-course-date": ["I", "AM" ,"YOUR" , "FATHER", "...", "NOOOOOOOOOOOOOOO"],
    "breadth-course-grade": ["I", "AM" ,"YOUR" , "FATHER", "...", "NOOOOOOOOOOOOOOO"],
    "concentration-course-info": ["YOU", "WERE" , "MY", "BROTHER"],
    "concentration-course-date":  ["YOU", "WERE" , "MY", "BROTHER"],
    "concentration-course-hours": ["6", "9" , "6", "9"],
    "other-course-info":  ["I", "HATTTTEEE" , "YOUUUUU", "UUUUUU"],
    "other-course-hours":  ["6", "9" , "6", "9"],
    "note": "I HATTTTE YOUUUUU",
    "other-courses": "YOU WERE MY BROTHER",
    "minor": "WE WERE SUPPOSED TO DESTROY THE SITH NOT JOIN THEM",
    "committee": ["I", "AM" ,"YOUR" , "FATHER", "...", "NOOOOOOOOOOOOOOO"],
    "chair-signature": "AJ",
    "reason-approved": "Your left arm",
    "director-signature": "LIAAAAR"
}

let CS06Dropdowns = {
    "comp915": "true",
    "breadth-course-category": ["S", "A" ,"O" , "T", "T", "T"],
    "background-prep-worksheet": "false",
    "program-product-requirement": "false",
    "phd-written-exam": "false",
    "phd-oral-exam": "true",
    "approved": "Approved",
}

describe('Test CS06 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS06 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS06').click();
        
        util.fillCleanFormAsAdmin(CS06);

        util.selectDropdowns(CS06Dropdowns);

        cy.get('.select-advisor6').click();
        cy.get('.select-chairman3').click();
        
        cy.get('.CS06-submit').click();

        cy.get('.advisor').should("have.value", CS06.committee[5]);
        cy.get('.chairman').should("have.value", CS06.committee[2]);

        util.checkFormAsAdmin(CS06);

        util.checkDropdowns(CS06Dropdowns);
    })

    it('Submit CS06 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS06/false')

        cy.get('.select-advisor4').click();
        cy.get('.select-chairman4').click();

        cy.contains(CS06["director-signature"]);
   
        delete CS06["director-signature"];
        delete CS06["student-name"];
        delete CS06["student-pid"];

        util.fillFormAsStudent(CS06);

        cy.get('.CS06-submit').click();

        util.checkFormAsStudent(CS06);

        cy.get('.advisor').should("have.value", CS06.committee[3]);
        cy.get('.chairman').should("have.value",CS06.committee[3]);

        
    });

})



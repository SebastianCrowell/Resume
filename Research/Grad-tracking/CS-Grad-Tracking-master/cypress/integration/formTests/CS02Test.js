import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS02 = {
    "date-submitted" : "Feb.2, 2020",
    "course-number": "COMP 560",
    "basis-waiver" : "Taken",
    "advisor-signature": data.admin.lastName,
    "advisor-date-signed": "YO yoyo",
    "instructor-signature": "jeffe",
    "instructor-date-signed": "time"
}

describe('Test CS02 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS02 form from administrator side', ()=>{
        

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS02').click();

        cy.get('.student-name').should('have.value', student.lastName + ", " + student.firstName)
        cy.get('.student-pid').should('have.value', student.pid.toString())

        
        util.fillCleanFormAsAdmin(CS02);
        
        cy.get('.CS02-submit').click();

        util.fillFormAsStudent(CS02);
    })

    it('Submit CS02 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS02/false')

        cy.get('.student-name').should('have.value', student.lastName + ", " + student.firstName)
        cy.get('.student-pid').should('have.value', student.pid.toString())

        cy.contains(CS02["advisor-signature"]);
        cy.contains(CS02["advisor-date-signed"]);
        cy.contains(CS02["instructor-signature"]);
        cy.contains(CS02["instructor-date-signed"]);
       
        delete CS02["advisor-signature"];
        delete CS02["advisor-date-signed"];
        delete CS02["instructor-signature"];
        delete CS02["instructor-date-signed"];

        util.fillFormAsStudent(CS02);

        cy.get('.CS02-submit').click();

        util.checkFormAsStudent(CS02);

    });

})



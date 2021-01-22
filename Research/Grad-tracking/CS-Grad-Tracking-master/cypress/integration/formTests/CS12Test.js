import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS12 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "email" : "@yahoo.com",
    "date" : "Here",
    "committee" : ["Admin", "Not Admin", "James", "Giant Peach", "Dong", "Tea"]
}

describe('Test CS12 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS12 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS12').click();
        
        util.fillCleanFormAsAdmin(CS12);

        cy.get('.CS12-submit').click();

        util.checkFormAsAdmin(CS12);
    })

    it('Submit CS12 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS12/false');

        delete CS12["student-name"];
        delete CS12["student-pid"];
        delete CS12["committee"];

        util.fillFormAsStudent(CS12);

        cy.get('.CS12-submit').click();

        util.checkFormAsStudent(CS12);

    });
})
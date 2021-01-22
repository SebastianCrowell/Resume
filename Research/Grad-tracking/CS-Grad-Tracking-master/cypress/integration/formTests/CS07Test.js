import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS07 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "comments" : "asdasdasdsadasdsadad",
    "chairman-signature": "asdasd",
    "chairman-date-signed": "asdasdqwc"
}

describe('Test CS07 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS07 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS07').click();
        
        util.fillCleanFormAsAdmin(CS07);

        cy.get('.CS07-submit').click();

        util.checkFormAsAdmin(CS07);
    })

    it('Submit CS07 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS07/false');

        cy.contains(CS07["chairman-signature"]);

        delete CS07["chairman-signature"];
        delete CS07["chairman-date-signed"];
        delete CS07["student-name"];
        delete CS07["student-pid"];

        util.fillFormAsStudent(CS07);

        cy.get('.CS07-submit').click();

        util.checkFormAsStudent(CS07);

    });

})
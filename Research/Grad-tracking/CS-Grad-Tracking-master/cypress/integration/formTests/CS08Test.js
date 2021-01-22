import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS08 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "semester" : "asdsadsa",
    "year" : "1293",
    "title" : "a",
    "primary-reader": "1asdasd",
    "primary-date": "1asdasdqwc",
    "secondary-reader": "2asdasd",
    "secondary-date": "2asdasdqwc",
    "primary-signature": "1asdasd",
    "primary-signed-date": "1asdasdqwc",
    "secondary-signature": "2asdasd",
    "secondary-signed-date": "2asdasdqwc",
}

describe('Test CS08 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS08 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS08').click();
        
        util.fillCleanFormAsAdmin(CS08);

        cy.get('.CS08-submit').click();

        util.checkFormAsAdmin(CS08);
    })

    it('Submit CS08 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS08/false');

        cy.contains(CS08["primary-reader"]);
        cy.contains(CS08["primary-date"]);
        cy.contains(CS08["secondary-reader"]);
        cy.contains(CS08["secondary-date"]);
        cy.contains(CS08["primary-signature"]);
        cy.contains(CS08["secondary-signature"]);
        cy.contains(CS08["primary-signed-date"]);
        cy.contains(CS08["secondary-signed-date"]);

        delete CS08["primary-reader"];
        delete CS08["primary-date"];
        delete CS08["secondary-reader"];
        delete CS08["secondary-date"];
        delete CS08["primary-signature"];
        delete CS08["secondary-signature"];
        delete CS08["primary-signed-date"];
        delete CS08["secondary-signed-date"];
        delete CS08["student-name"];
        delete CS08["student-pid"];

        util.fillFormAsStudent(CS08);

        cy.get('.CS08-submit').click();

        util.checkFormAsStudent(CS08);

    });
})
import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS04 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "date-submitted": "Feb.2, 2020",
    "project-description": "ASDF",
    "student-signature": "AA",
    "student-date-signed": "AAA",
    "chairman-signature": "ASDF",
    "chairman-date-signed": "ASSDF",
    

}

let CS04Dropdowns = {
    "doc-proprietary": "false",
    "approved": "false"
}

describe('Test CS04 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS04 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS04').click();
        
        util.fillCleanFormAsAdmin(CS04);

        util.selectDropdowns(CS04Dropdowns);
        
        cy.get('.CS04-submit').click();

        util.checkFormAsAdmin(CS04);

        util.checkDropdowns(CS04Dropdowns);
    })

    it('Submit CS04 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS04/false')

        cy.contains(CS04Dropdowns.approved)
        cy.contains(CS04["chairman-signature"]);
        cy.contains(CS04["chairman-date-signed"]);

        delete CS04["chairman-signature"]
        delete CS04.approved;
        delete CS04["chairman-date-signed"];
        delete CS04["student-name"];
        delete CS04["student-pid"];


        util.fillFormAsStudent(CS04);

        cy.get('.CS04-submit').click();

        util.checkFormAsStudent(CS04);

        
    });

})



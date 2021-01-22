import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS01BSMS = {
    covered521: 'A',
    date521: 'A',
    covered520: 'B',
    date520: 'B',
    covered530: 'C',
    date530: 'C',
    covered524: 'D',
    date524: 'D',
    covered541: 'E',
    date541: 'E',
    covered661: 'G',
    date661: 'G',
    "student-signed": student.lastName,
    "student-signed-date": 'asd',
    "advisor-signed": data.admin.lastName,
    "advisor-signed-date": 'casc'
}

describe('Test CS01MSBS submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS01MSBS form from administrator side', ()=>{

        cy.visit('/changeUser/student')
        cy.visit('/changeUser/admin');
        cy.visit('/student');

        util.visitFormAsAdmin();

        cy.get('.CS01BSMS').click();

        cy.get('.student-name').should('have.value', student.lastName + ", " + student.firstName)
        cy.get('.student-pid').should('have.value', student.pid.toString())

        util.fillCleanFormAsAdmin(CS01BSMS);
        
        cy.get('.CS01BSMS-submit').click();

        util.checkFormAsAdmin(CS01BSMS);
    });

    it('Submit CS01BSMS form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS01BSMS/false')

        cy.get('.student-name').should('have.value', student.lastName + ", " + student.firstName)
        cy.get('.student-pid').should('have.value', student.pid.toString())

        cy.contains(CS01BSMS["advisor-signed"]);
        cy.contains(CS01BSMS["advisor-signed-date"]);

        delete CS01BSMS["advisor-signed"];
        delete CS01BSMS["advisor-signed-date"];

        util.fillFormAsStudent(CS01BSMS);

        cy.get('.CS01BSMS-submit').click();

        util.checkFormAsStudent(CS01BSMS);

    });

})
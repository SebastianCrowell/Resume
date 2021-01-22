import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS05 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "date-submitted": "Feb.2, 2020",
    "nominees": ["asdas", "ASDF" ,"asdjkn" , "skda", "sjakdl"],
    "nominee-departments": ["asdas", "ASDF" ,"asdjkn" , "skda", "sjakdl"],
    "nominee-statuses": ["asdas", "ASDF" ,"asdjkn" , "skda", "sjakdl"],
    "director-signature": "ASDF",
    "director-date-signed": "ASSDF"
}

let CS05Dropdowns = {
    "oral-comprehensive-exam": "false",
    "thesis": "false"
}

describe('Test CS05 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS05 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS05').click();
        
        util.fillCleanFormAsAdmin(CS05);

        util.selectDropdowns(CS05Dropdowns);

        cy.get('.select-advisor1').click();
        cy.get('.select-chairman3').click();
        
        cy.get('.CS05-submit').click();

        cy.get('.thesis-advisor').should("have.value", CS05.nominees[0]);
        cy.get('.committee-chairman').should("have.value", CS05.nominees[2]);

        util.checkFormAsAdmin(CS05);

        util.checkDropdowns(CS05Dropdowns);
    })

    it('Submit CS05 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS05/false')

        cy.get('.select-advisor2').click();
        cy.get('.select-chairman4').click();

        cy.contains(CS05["director-signature"]);
        cy.contains(CS05["director-date-signed"]);
   
        delete CS05["director-signature"];
        delete CS05["director-date-signed"];
        delete CS05["student-name"];
        delete CS05["student-pid"];
        delete CS05["nominee-statuses"];

        util.fillFormAsStudent(CS05);

        cy.get('.CS05-submit').click();

        util.checkFormAsStudent(CS05);

        cy.get('.thesis-advisor').should("have.value", CS05.nominees[1]);
        cy.get('.committee-chairman').should("have.value",CS05.nominees[3]);

        
    });

})



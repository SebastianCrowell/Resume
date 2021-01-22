import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS13 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "email" : "@yahoo.com",
    "date" : "Here",
    "comp523-signature" : "asdas",
    "comp523-name": "dddd",
    "job-info": "ssss",
    "advisor-name": "asdasd",
    "advisor-signature": "NAme",
    "product": "WO",
    "client": "Tony",
    "position": "HEAD",
    "alt-signature1": "Aad",
    "alt-signature2": "Peep"
}

let CS13Dropdowns = {
    "comp523": "true",
    "had-job": "false",
    "alternative": "true"
}

describe('Test CS13 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS13 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS13').click();
        
        util.fillCleanFormAsAdmin(CS13);

        util.selectDropdowns(CS13Dropdowns);

        cy.get('.CS13-submit').click();

        util.checkFormAsAdmin(CS13);
    })

    it('Submit CS13 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS13/false');

        cy.contains(CS13["advisor-signature"]);
        cy.contains(CS13["alt-signature1"]);
        cy.contains(CS13["alt-signature2"]);
        cy.contains(CS13["comp523-signature"]);

        delete CS13["student-name"];
        delete CS13["student-pid"];
        delete CS13["advisor-name"];
        delete CS13["advisor-signature"];
        delete CS13["comp523-name"];
        delete CS13["comp523-signature"];
        delete CS13["alt-signature1"];
        delete CS13["alt-signature2"];

        util.fillFormAsStudent(CS13);

        cy.get('.CS13-submit').click();

        util.checkFormAsStudent(CS13);

    });
})
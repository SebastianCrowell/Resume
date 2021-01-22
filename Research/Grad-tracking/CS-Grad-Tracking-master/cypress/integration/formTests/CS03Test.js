import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS03 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "date-submitted": "Feb.2, 2020",
    "DR": ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
    "dept": ["COMP", "COMP", "COMP", "COMP", "COMP", "COMP", "COMP", "COMP", "COMP", "COMP"],
    "course": ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
    "hours": ['1', '3', '4', '5', '2', '3', '5', '6', '7', '2'],
    "semester": ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
    "title": ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
    "student-signature": "A",
    "advisor-signature": "A",
    "approval-reason": "ASDF",
    "director-signature": "ASDF",
    "director-date-signed": "YOYOYO"
}

let CS03Dropdowns = {
    "background-prep": "true",
    "program-product": "false",
    "comprehensive-paper": "true",
    "thesis": "true",
    "outside-review": "true",
    "comprehensive-exam": "Comprehensive Paper",
    "approved": "Approved by Graduate Studies Committee"
}

describe('Test CS03 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS03 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS03').click();
        
        util.fillCleanFormAsAdmin(CS03);

        util.selectDropdowns(CS03Dropdowns);
        
        cy.get('.CS03-submit').click();

        util.checkFormAsAdmin(CS03);

        util.checkDropdowns(CS03Dropdowns);
    })

    it('Submit CS03 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS03/false')

        cy.contains(CS03Dropdowns.approved)
        cy.contains(CS03["advisor-signature"]);
        cy.contains(CS03["approval-reason"]);
        cy.contains(CS03["director-signature"]);
        cy.contains(CS03["director-date-signed"]);

        delete CS03["advisor-signature"];
        delete CS03.approved;
        delete CS03["approval-reason"];
        delete CS03["director-signature"];
        delete CS03["director-date-signed"]; 
        delete CS03["student-name"];
        delete CS03["student-pid"];
        //maybe don't autofill this on the html
        delete CS03.dept;


        util.fillFormAsStudent(CS03);

        cy.get('.CS03-submit').click();

        util.checkFormAsStudent(CS03);

        
    });

})



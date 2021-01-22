import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS11 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "full-responsibility" : "Wisdom is the offspring of Suffering and time.",
    "partial-responsibility" : "There is a fine line between consideration and hesitation. The former is widom. The latter is fear.",
    "semester": "To entertain doubt is to dance with death",
    "year": "2016",
    "supervisor": "The throne is the most devious trap of them all.",
    "supervisor-signature": "An emperor is only efficient as those he commands.",
    "supervisor-date-signed": "When one defiles the effigy, one defiles the emperor.",
    "other": "Some things that slumber should never be awoken.",
    "director-signature": "Give a man a fish, and you can feed him for a day. But give a fish a man, and you can feed it for a month.",
    "director-date-signed": "I fought for God. Who do you fight for, exile?"
}

let CS11Dropdowns = {
    "approved": "Approved",
}

describe('Test CS11 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS11 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS11').click();
        
        util.fillCleanFormAsAdmin(CS11);

        util.selectDropdowns(CS11Dropdowns);

        cy.get('.CS11-submit').click();

        util.checkFormAsAdmin(CS11);
    })

    it('Submit CS11 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS11/false');

        let deletions = ["director-signature", "director-date-signed"];
        delete CS11["student-name"];
        delete CS11["student-pid"];
        for(let i = 0; i < deletions.length; i++){
            cy.contains(CS11[deletions[i]]);
            delete CS11[deletions[i]];
        }

        util.fillFormAsStudent(CS11);

        cy.get('.CS11-submit').click();

        util.checkFormAsStudent(CS11);

    });
})
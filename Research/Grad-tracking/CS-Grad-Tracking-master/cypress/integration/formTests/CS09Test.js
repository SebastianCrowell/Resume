import data from '../../../data/testRoles';
import util from './formUtil';

let student = data.student;

let CS09 = {
    "student-name": student.lastName + ", " + student.firstName,
    "student-pid": student.pid.toString(),
    "prp-title" : "I ate two socks",
    "research-advisor" : "WITNESS!",
    "authors": "IMMORTAN JOE",
    "paper-notify-date": "MAD MAXXXX",
    "research-responsible": "So metaphorical",
    "present": "Oh, I see you have a plan son",
    "advisor-signature": "*While hugging rock* I will make things right Dad",
    "advisor-date-signed": "You've crossed the line!",
    "committee-signature": ["Omae", "Wa", "Mou", "Shindeiru"],
    "committee-date-signed": ["NANI?????", "*Intense high pitched noise*", "*Explosion*", "ARRRRRRGHHHH"],
    "presentation-date": "*Weird whispy ghost* Who.... are you?",
    "feedback": "This is Alterra HQ. This may be our only communications window. We can't send a rescue ship all the way out there so Aurora, you're just going to have to meet us halfway. *Offmic* We're doing a sandwich run, are you in?"
}

let CS09Dropdowns = {
    "peer-reviewed": "Yes",
    "paper-accepted": "Yes",
    "reviews-available": "No",
}

let CS09Sliders = {
    "concept-integration": 1,
    creativity: 1,
    clarity: 3,
    "abstraction-formality": -2,
    organization: 2,
    writing: .5,
    presentation: .25,
    "answering-question": -.5,

}

describe('Test CS09 submissions', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Submit CS09 form from administrator side', ()=>{

        cy.visit('/changeUser/student');
        cy.visit('/changeUser/admin');
        
        util.visitFormAsAdmin();

        cy.get('.CS09').click();
        
        util.fillCleanFormAsAdmin(CS09);

        for(let element in CS09Sliders){
            cy.get('.' + element)
            .invoke('val', CS09Sliders[element])
            .trigger('input', {force: true})
        }

        util.selectDropdowns(CS09Dropdowns);

        cy.get('.CS09-submit').click();

        util.checkFormAsAdmin(CS09);
    })

    it('Submit CS09 form from student side', ()=>{
        cy.visit('/changeUser/student');
        cy.visit('/studentView/forms/CS09/false');

        let deletions = ["advisor-signature", "advisor-date-signed", "presentation-date", "feedback"];
        delete CS09["student-name"];
        delete CS09["student-pid"];
        for(let i = 0; i < deletions.length; i++){
            cy.contains(CS09[deletions[i]]);
            delete CS09[deletions[i]];
        }

        for(let i = 0; i < CS09["committee-signature"].length; i++){
            cy.contains(CS09["committee-signature"][i])
            cy.contains(CS09["committee-date-signed"][i]);
        }
        delete CS09["committee-date-signed"];
        delete CS09["committee-signature"];
       
        // for(let key in CS09Sliders){
        //     cy.get('.' + key).should('have.value', CS09Sliders[key]);
        // }

        util.fillFormAsStudent(CS09);

        cy.get('.CS09-submit').click();

        util.checkFormAsStudent(CS09);

    });
})
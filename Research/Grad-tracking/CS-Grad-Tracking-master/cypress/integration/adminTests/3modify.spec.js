var data = require('./data.js');

const studentTextFields = data.studentTextFields;

const job = data.job;

const studentDropdownFields = data.studentDropdownFields;

const course = data.course;

const note = data.note;

const visitSingleStudent = ()=>{
    cy.visit('/student');

    data.searchStudentHelper();

    cy.get('.edit-student-button').click();
}

describe("Mongoose relation tests (assigning students to jobs, courses)", ()=>{
    
    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Should be able to assign a job to a student on the job assign page', ()=>{

        cy.visit('/changeUser/admin');
        
        cy.visit('/job');

        data.searchJobHelper();

        cy.get('.assign-job-button').click();

        cy.contains(job.position); cy.contains(job.supervisor); cy.contains(job.description);

        cy.get('.assign-student-select')
        .select(studentTextFields["last-name"]+", "+studentTextFields["first-name"]);
        
        cy.get('.assign-job-submit-button').click();

        cy.wait(2000);

        cy.get('.student-holding-job-table').find('tr').should('have.length', 2);

        cy.get('.student-holding-job-table > tbody > tr > td').eq(0).contains(studentTextFields["last-name"]);

        cy.get('.unassign-job-submit-button').click();

        cy.contains('No students hold this job');
    });
    
    it('Should be able to assign a job to a student from the student\'s page', ()=>{
        visitSingleStudent();

        cy.get('.student-navigation-jobs-button').click();

        //assign the student the same job twice to test the
        //desired behaviour of no duplicate job when assigning the same job twice
        for(var i = 1; i <= 2; i++){
            cy.get('.job-select > option')
            .eq(1)
            .then((element) => cy.get('.job-select').select(element.val()));

            cy.get('.job-select-submit-button').click();

            cy.get('.job-history-table').find('tr').should('have.length', 2);
            cy.get('.job-history-table').contains(job.position)
            cy.get('.job-history-table').contains(job.supervisor);
        }

        cy.get('.job-delete-button').click();

        cy.contains('No jobs found.');

    })

    it('Should be able to add a note', ()=>{
        visitSingleStudent();

        cy.get('.student-navigation-notes-button').click();

        cy.get('.new-note-title-input')
        .type(note.title);

        cy.get('.new-note-text-input')
        .type(note.note);

        cy.get('.new-note-submit-button').click();

    })


    it('Should be able to update a note', ()=>{
        var tempNote = {
            title: "ABC",
            note: "EAT EVERYTHING"
        }

        cy.get('.edit-note-title-input')
        .type(tempNote.title);

        cy.get('.edit-note-text-input')
        .type(tempNote.note);

        cy.get('.edit-note-submit-button').click();

        cy.get('.edit-note-title-input')
        .should('have.value', note.title+tempNote.title);

        cy.get('.edit-note-text-input')
        .should('have.value', note.note+tempNote.note);
    })

    it('Should be able to delete a note', ()=>{

        cy.get('.note-delete-button').click()

        cy.get('.note-list')
        .should('not.have.text', note.title);
    })

    //add form section

})

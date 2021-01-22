var data = require('./data.js');

const studentTextFields = data.studentTextFields;

const job = data.job;

const studentDropdownFields = data.studentDropdownFields;

const course = data.course;

describe("Delete data", ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    

    it('Searching for a job should return the single job and you should be able to delete it', ()=>{

        cy.visit('/changeUser/admin');
        
        cy.visit('/job');

        data.searchJobHelper();

        cy.get('.job-table').find('tr').should('have.length', 2);

        //first column is position, check if it contains the expected value
        cy.get('tbody > tr > td').eq(0).contains(job.position);

        cy.get('tbody > tr > td').eq(1).contains(job.supervisor);

        cy.get('tbody > tr > td').eq(4).contains(job.hours);

        cy.get('.delete-job-button').click();

        data.searchJobHelper();

        cy.contains('No jobs found.');
    })

    

    it('Searching for a course should return the single course and you should be able to delete it', ()=>{
        cy.visit('/course');

        /*
        Right now, I can't think of a good solution for getting the name/course number
        to search for the course that was created earlier, because that data 
        was uploaded in a separate csv and is not defined in this file. 
        */
        searchCourseHelper();

        cy.get('.course-table').find('tr').should('have.length', 2);

        cy.get('tbody > tr > td').eq(0).contains('065');

        cy.get('tbody > tr > td').eq(3).contains('FYS');

        cy.get('.delete-course-button').click();

        searchCourseHelper();

        cy.contains('No courses found.');
    })

    function searchCourseHelper(){
        cy.get('input[name=number]')
        .type('065')

        cy.get('input[name=name]')
        .type('FYS')

        cy.get('.search-course-button').click();
    }

    it('Searching for a student should return the single student and you should be able to delete it', ()=>{
        cy.visit('/student');

        data.searchStudentHelper();

        cy.get('.student-table').find('tr').should('have.length', 2);

        //select first value of table to make sure it is the student we searched for
        cy.get('tbody > tr > td').eq(0).contains(studentTextFields.onyen);

        cy.get('.delete-student-submit').click();

        data.searchStudentHelper();

        cy.contains('No student found.');
    });
})
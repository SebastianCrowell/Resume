var data = require('./data');

describe("Page visit and page javascript", ()=>{
    
    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Visiting the homepage as admin should route to /student', ()=>{
        cy.visit('/changeUser/admin');
        
        cy.visit('/');

        cy.url().should('include', '/student');
    });

    it('Clicking on links in sidebar should route to correct pages', ()=>{
        
        cy.visit('/student');

        const sidebarButtons = {
            '.report-button': '/report',
            '.course-button': '/course',
            '.faculty-button': '/faculty',
            '.job-button': '/job',
            '.student-button': '/student'
        }

        for(var key in sidebarButtons){
            cy.get(key).click();
            cy.url().should('contain', sidebarButtons[key]);
        }
    });

    it('Clicking page specific sidebar links should route to correct pages', ()=>{

        cy.visit('/course');

        cy.get('.create-course-button').click();
        cy.url().should('contain', '/course/create');

        cy.get('.upload-course-button').click();
        cy.url().should('contain', '/course/upload');

        cy.get('.upload-course-info-button').click();
        cy.url().should('contain', '/course/uploadInfo');


        cy.visit('/faculty');

        cy.get('.create-faculty-button').click();
        cy.url().should('contain', '/faculty/create');

        cy.get('.upload-faculty-button').click();
        cy.url().should('contain', '/faculty/upload');

        
        cy.visit('/job');

        cy.get('.create-job-button').click();
        cy.url().should('contain', '/job/create');

        cy.get('.upload-job-button').click();
        cy.url().should('contain', '/job/upload');

        cy.get('.upload-grant-button').click();
        cy.url().should('contain', '/job/uploadGrant');


        cy.visit('/student')

        cy.get('.create-student-button').click();
        cy.url().should('contain', '/student/create');

        cy.get('.upload-student-button').click();
        cy.url().should('contain', '/student/upload');

        cy.get('.upload-courses-button').click();
        cy.url().should('contain', '/student/uploadCourses');

        //add section that clicks on student top nav bar

        cy.visit('/student');

        data.searchStudentHelper();

        cy.get('.edit-student-button').click();

        cy.get('.student-navigation-edit-button').click()
        cy.url().should('contain', '/student/edit');

        cy.get('.student-navigation-jobs-button').click();
        cy.url().should('contain', '/student/jobs');

        cy.get('.student-navigation-forms-button').click();
        cy.url().should('contain', '/student/forms');

        cy.get('.student-navigation-courses-button').click();
        cy.url().should('contain', '/student/courses');

        cy.get('.student-navigation-notes-button').click();
        cy.url().should('contain', '/student/notes');
    });

    it('Javascript on job page should correctly hide elements', ()=>{
        cy.visit('/job/create');

        cy.get('.input-semester-block')
        .should('be.visible');

        cy.get('.input-position')
        .select("TA")
        .should('have.value', "TA");

        cy.get('.input-semester-block')
        .should('not.be.visible');

        cy.get('.input-position')
        .select("RA");

        cy.get('.input-semester-block')
        .should('be.visible');

        cy.get('.input-position')
        .select("OTHER")

        cy.get('.input-semester-block')
        .should('be.visible');

    })

});
var student = require('../../../data/testRoles').student

describe('Test student routes and functionality', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })

    it('Make sure student can not access job, course, and student create routes', ()=>{
        cy.visit('/changeUser/student');
        
        cy.visit('/student');
        cy.contains('Not faculty');

        cy.visit('/course');
        cy.contains('Not admin');

        cy.visit('/job');
        cy.contains('Not admin');

        cy.visit('/faculty');
        cy.contains('Not admin');
    })
    
    it('When logged in as student, get taken to studentView page', ()=>{
        cy.visit('/changeUser/student')

        cy.visit('/')
        cy.url().should('include','/studentView')
    })

    var updateStudent = {
        "first-name":"NEWNAME",
        "last-name":"NEWLAST",
        "alternative-name":"ALT NAME",
        "gender":"MALE",
        "ethnicity":"ASIAN",
        "residency": "APPLIED"
    }

    it('Able to update some basic info on the student page', ()=>{
        cy.get('.input-first-name')
        .clear()
        .type(updateStudent["first-name"])

        cy.get('.input-last-name')
        .clear()
        .type(updateStudent["last-name"])

        cy.get('.input-alt-name')
        .clear()
        .type(updateStudent["alternative-name"])

        cy.get('.select-gender')
        .select(updateStudent.gender)

        cy.get('.select-ethnicity')
        .select(updateStudent.ethnicity)

        cy.get('.select-residency')
        .select(updateStudent.residency)

        cy.get('.update-button-submit').click()

        for(var key in updateStudent){
            console.log(updateStudent[key])
            cy.get('.student-table').contains(updateStudent[key])
        }

        cy.get('.select-gender')
        .select('OTHER')
        cy.get('.select-ethnicity')
        .select('OTHER')
        cy.get('.select-residency')
        .select('NO')
        cy.get('.update-button-submit').click();

    })

    it('Should be able to click top navigation bar and be brought to correct pages', ()=>{
        cy.visit('/')

        cy.get('.student-jobs').click()
        cy.url().should('include', 'studentView/jobs')
        cy.get('.student-forms').click()
        cy.url().should('include', 'studentView/forms')
        cy.get('.student-profile').click()
        cy.url().should('include', 'studentView')
        cy.get('.student-courses').click()
        cy.url().should('include', 'studentView/courses')
    })

    it('Student should be able to see a job they are holding', ()=>{
        
        cy.visit('/changeUser/admin')
        cy.visit('/job/create')

        const studJob = {
            position: "RA",
            supervisor: "admin, admin",
            semester: "FA 2018"
        }

        cy.get('.input-position').select(studJob.position)
        cy.get('.input-supervisor').select(studJob.supervisor)
        cy.get('.input-semester').select(studJob.semester)
        cy.get('.submit-job').click()
        cy.visit('/job')
        cy.get('.assign-job-button').click()
        cy.get('.assign-student-select').select(updateStudent["last-name"] + ", "+updateStudent["first-name"])
        cy.get('.assign-job-submit-button').click()

        cy.visit('/changeUser/student')
        cy.visit('/studentView/jobs')
        cy.get('.student-job-table').find('tr').should('have.length', 2);
        cy.contains(studJob.position)
        cy.contains(studJob.supervisor)
        cy.contains(studJob.semester)
    })

    
    //include form tests and other tests as issues come up
})
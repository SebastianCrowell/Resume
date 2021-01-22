
describe('Test the routes that a faculty should/should not be able to access', ()=>{

    beforeEach(function () {
        Cypress.Cookies.preserveOnce('connect.sid')
    })
    
    it('Make sure faculty can not access job, course, and student create routes', ()=>{

        cy.visit('/changeUser/faculty');
        
        cy.visit('/student/create');
        cy.contains('Not admin');

        cy.visit('/course');
        cy.contains('Not admin');

        cy.visit('/course/create');
        cy.contains('Not admin');

        cy.visit('/job');
        cy.contains('Not admin');

        cy.visit('/job/create');
        cy.contains('Not admin');
    })
    
    //for now we have faculty able to see all students but not edit any of them.
    it('Make sure faculty can see students, their jobs, their forms, and their courses,'+
    ' no matter if they are the student\'s advisor or not. Make sure they can add notes no matter if they are an advisor or not for now.', ()=>{

        cy.visit('/changeUser/admin');
        cy.visit('/student/create');

        const student = {
            onyen : 'abc',
            csid : 'abc',
            email : 'abc@fake.com',
            'first-name' : 'abc',
            'last-name' : 'abc',
            pid : '232232232',
        }
        for(var key in student){
            cy.get('.input-'+key)
            .type(student[key])
            .should('have.value', student[key]);
        }
        //faculty, faculty defined in changeUser route
        cy.get('.input-advisor')
        .select('faculty, faculty');

        cy.get('.create-student-submit').click();

        cy.visit('/changeUser/faculty');

        cy.visit('/student');

        cy.get('.edit-student-button').click();
        cy.contains('Edit student');

        cy.get('.student-navigation-edit-button').click();
        cy.contains('Edit student');

        cy.get('.student-navigation-jobs-button').click();
        cy.contains(student["first-name"]);

        cy.get('.student-navigation-forms-button').click();
        cy.contains(student["first-name"]);

        cy.get('.student-navigation-courses-button').click();
        cy.contains(student["first-name"]);

        cy.get('.student-navigation-notes-button').click();
        cy.contains(student["first-name"]);
        
        const note = "I WROTE A NOTE";
        cy.get('.new-note-title-input')
        .type(note);

        cy.get('.new-note-text-input')
        .type(note);

        cy.get('.new-note-submit-button').click();
        cy.contains(note);

    })

    /*
    Sometimes, faculty are needed to provide their signatures for a student's forms,
    even if they are not the student's advisor. However, it is a pain to allow
    a random faculty to only be able to edit the signature part on forms, and not allow
    them to edit everything else, perhaps just make it so that the admin/advisor has
    to do it, and the faculty comes in-person to the admin/advisor's computer and signs then.
    */



})
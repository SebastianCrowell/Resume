let util = {};
util.visitFormAsAdmin = ()=> {
    cy.visit('/student');
    cy.get('.search-pid').type(777777777);
    cy.get('.search-student-submit').click();
    cy.get('.edit-student-button').click();
    cy.get('.student-navigation-forms-button').click();
}

util.visitFormAsStudent = ()=> {

}

util.fillCleanFormAsAdmin = (formData)=>{
    for(let key in formData){
        if(Array.isArray(formData[key])){
            for(var i = 1; i <= formData[key].length; i++){
                cy.get('.' + key + i).clear().type(formData[key][i - 1]);
            }
        }
        else{
            cy.get('.' + key).clear().type(formData[key]);
        }
    }
}

util.checkFormAsAdmin = (formData)=>{
    for(let key in formData){
        if(Array.isArray(formData[key])){
            for(var i = 1; i <= formData[key].length; i++){
                cy.get('.' + key + i).should('have.value', formData[key][i - 1]);
            }
        }
        else{
            cy.get('.' + key).should('have.value', formData[key]);
        }
    }
}

util.fillFormAsStudent = (formData)=>{
    for(let key in formData){
        if(Array.isArray(formData[key])){
            for(var i = 1; i <= formData[key].length; i++){
                cy.get('.' + key + i).should('have.value', formData[key][i - 1]).clear().type(formData[key][i - 1] + 1);;
            }
        }
        else{
            cy.get('.' + key).should('have.value', formData[key]).clear().type(formData[key] + 1);
        }
    }
}

util.checkFormAsStudent = (formData)=>{
    for(let key in formData){
        if(Array.isArray(formData[key])){
            for(var i = 1; i <= formData[key].length; i++){
                cy.get('.' + key + i).should('have.value', formData[key][i - 1] + 1);
            }
        }
        else{
            cy.get('.' + key).should('have.value', formData[key] + 1);
        }
    }
}

util.selectDropdowns = (formData) => {

    for(let key in formData){
        if(Array.isArray(formData[key])){
            for(var i = 1; i <= formData[key].length; i++){
                cy.get('.'+key+i).select(formData[key][i - 1]).should('have.value', formData[key][i - 1]);
            }
        }
        else{
            cy.get('.'+key).select(formData[key]).should('have.value', formData[key]);
        }
    }
}

util.checkDropdowns = (formData) => {

    for(let key in formData){

        if(Array.isArray(formData[key])){
            for(var i = 1; i <= formData[key].length; i++){
                cy.get('.'+key+i).should('have.value', formData[key][i - 1]);
            }
        }
        else{
            cy.get('.' + key).should('have.value', formData[key]);
        }

        
    }
}

module.exports = util;
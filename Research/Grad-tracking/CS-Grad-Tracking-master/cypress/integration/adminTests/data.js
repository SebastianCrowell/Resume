var data = {};

const currentDate = "2019-09-19";
const pastDate = "2019-02-02";

data.studentTextFields = {
    onyen : 'fakeonyen',
    csid : 'fakecsid',
    email : 'fakeEmail@fake.com',
    'first-name' : 'fake',
    'last-name' : 'fake',
    pid : '949949949',
    'alternative-name': 'fake',
    'entering-status': 'help',
    'research-area': 'Systems',
    'leave-extension': 'NO',
    'hours-completed': '20',
    'semesters-on-leave': '99',
    'background-approved': currentDate,
    'masters-awarded': pastDate,
    'prp-passed': currentDate,
    'technical-writing-approved': currentDate,
    'background-prep-worksheet-approved': currentDate,
    'program-of-study-approved': currentDate,
    'research-planning-meeting': currentDate,
    'program-product-requirement': currentDate,
    'committee-comp-approved': currentDate,
    'phd-proposal-approved': currentDate,
    'phd-awarded': pastDate,
    'oral-exam-passed': currentDate,
    'dissertation-defence-passed': currentDate,
    'oral-exam-passed': currentDate,
    'dissertation-submitted': currentDate,
}

data.job = {
    position: "TA",
    supervisor: "admin, admin",
    description: "A TA JOB",
    hours: "4",
}

data.studentDropdownFields = {
    pronouns: 'she, her',
    status: 'Active',
    gender: 'FEMALE',
    ethnicity: 'OTHER',
    residency: 'YES',
    'intended-degree': 'MASTERS',
    'funding-eligibility': 'GUARANTEED',
}
data.course = {
    univNumber: '1234',
    category: 'Theory',
    topic: 'N/A',
    section: '1',
    faculty: 'admin, admin',
    semester: 'FA 2018'
}

data.searchStudentHelper = ()=>{
    cy.get('.search-last-name')
    .type(data.studentTextFields["last-name"])
    .should('have.value', data.studentTextFields["last-name"])

    cy.get('.search-pid')
    .type(data.studentTextFields.pid)
    .should('have.value', data.studentTextFields.pid)

    cy.get('.search-student-submit').click();
}


data.searchJobHelper = ()=>{
    cy.get('input[name=position]')
    .type(data.job.position);

    cy.get('select[name=supervisor]')
    .select(data.job.supervisor);

    cy.get('.search-job-submit').click();
}

data.note = {
    title: "Today",
    note: "I had a dream."
}

module.exports = data;
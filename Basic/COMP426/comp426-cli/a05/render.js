/**
 * Course: COMP 426
 * Assignment: a05
 * Author: <type your name here>
 *
 * This script uses jQuery to build an HTML page with content taken from the
 * data defined in data.js.
 */



/**
 * Given a hero object (see data.js), this function generates a "card" showing
 *     the hero's name, information, and colors.
 * @param hero  A hero object (see data.js)
 */
export const renderHeroCard = function(hero) {
    // TODO: Copy your code from a04 to render the hero card
    return `
        <card id=${hero.id}>
        <div style="background-color:${hero.backgroundColor}">
        <img src="${hero.img}"></img>
        <span>${hero.subtitle}</span>
        </div>
        <p> 
        <div style="background-color:${hero.color}">
        <card>
        <p>${hero.name}</p>
        <p>${hero.firstSeen}</p>
        <p>${hero.first}</p>
        <div>${hero.last}</div>
        <div>${hero.description}</div>
        </div>
        </p>
        <button class="edit" value="${hero.id}">edit</button>
        </card>
        </card>
        `;
};



/**
 * Given a hero object, this function generates a <form> which allows the
 *     user to edit the fields of the hero. The form inputs should be
 *     pre-populated with the initial values of the hero.
 * @param hero  The hero object to edit (see data.js)
 */
export const renderHeroEditForm = function(hero) {
    // TODO: Copy your code from a04 to render the hero edit form
    return `
    <card id=${hero.id}>
    <form> 
    <button class="submit" type="submit" value="${hero.id}">save</button>
    <button class="cancel" type="cancel" value="${hero.id}">cancel</button>
    <input id="inputName" value=${hero.name}></input>
    <input id="inputFirst" value=${hero.first}></input>
    <input id="inputLast" value=${hero.last}></input>
    <input id="inputFirstSeen" value="${hero.firstSeen}"></input>
    <textarea id="inputText" >${hero.description}</textarea>
    </form>
    </card>
    `;
};



/**
 * Handles the JavaScript event representing a user clicking on the "edit"
 *     button for a particular hero.
 * @param event  The JavaScript event that is being handled
 */
export const handleEditButtonPress = function(event) {
    // TODO: Render the hero edit form for the clicked hero and replace the
    //       hero's card in the DOM with their edit form instead

    //console.log(heroicData[heroid - 1]);
    //console.log(event.currentTarget.value);
    //const heroF = renderHeroEditForm(heroicData[heroid - 1]);
    //event.currentTarget.closest("#root").replaceWith(heroF);

    let heroid = event.currentTarget.value;
    let h = heroicData.find(h => h.id == heroid);
    // let heroF = renderHeroEditForm(h);
    // let heroR = event.currentTarget.closest("card");
    // let divpieces = heroR.replaceWith(heroF);
    // console.log(divpieces)
    $("#root").find("#" + heroid).replaceWith(renderHeroEditForm(h));
    //console.log($("#root").find("#" + heroid).replaceWith(renderHeroEditForm(h)));
};



/**
 * Handles the JavaScript event representing a user clicking on the "cancel"
 *     button for a particular hero.
 * @param event  The JavaScript event that is being handled
 */
export const handleCancelButtonPress = function(event) {
    // TODO: Render the hero card for the clicked hero and replace the
    //       hero's edit form in the DOM with their card instead

    event.preventDefault();

    let heroid = event.currentTarget.value;
    let h = heroicData.find(h => h.id == heroid);

    let orginalName = h.name;
    let orginalFirst = h.first;
    let orginalLast = h.last;
    let orginalFirstSeen = h.firstSeen;
    let orginalTextArea = h.description;
    
    //console.log($("#root").find('#inputName').val(orginalName))
    $("#root").find('#inputName').val(orginalName);
    //console.log($("#root").find('#inputFirst').val(orginalFirst))
    $("#root").find('#inputFirst').val(orginalFirst);
    //console.log($("#root").find('#inputLast').val(orginalLast))
    $("#root").find('#inputLast').val(orginalLast);
    //console.log($("#root").find('#inputFirstSeen').val(orginalFirstSeen))
    $("#root").find('#inputFirstSeen').val(orginalFirstSeen);
    //console.log($("#root").find('#inputText').val(orginalTextArea))
    $("#root").find('#inputText').val(orginalTextArea);

    //console.log($("#root").find("#" + heroid).replaceWith(renderHeroCard(h)))
    $("#root").find("#" + heroid).replaceWith(renderHeroCard(h));
};



/**
 * Handles the JavaScript event representing a user clicking on the "cancel"
 *     button for a particular hero.
 * @param event  The JavaScript event that is being handled
 */
export const handleEditFormSubmit = function(event) {
    // TODO: Render the hero card using the updated field values from the
    //       submitted form and replace the hero's edit form in the DOM with
    //       their updated card instead

    let heroid = event.currentTarget.value;
    let h = heroicData.find(h => h.id == heroid);

    event.preventDefault()

    let inputName = $("#root").find('#inputName').val();
    let inputFirst = $("#root").find('#inputFirst').val();
    let inputLast = $("#root").find('#inputLast').val();
    let inputFirstSeen = $("#root").find('#inputFirstSeen').val();
    let inputText = $("#root").find('#inputText').val();

    let newDate = new Date(inputFirstSeen);
    let getTZ = newDate.toString().split(" ");
    let temp = getTZ[4] + " " + tzReduce(getTZ[5]);
    let tz = temp.slice(0, 1) + temp.slice(10, 11) + temp.slice(2, 8); 
    //console.log(tz);
    let changeDate = newDate.getFullYear() + "-" + zero((newDate.getMonth() + 1)) + "-" + zero(newDate.getDate()) + "T" + tz + "Z";
    console.log(changeDate)

    h.name = inputName;
    h.first = inputFirst;
    h.last = inputLast;
    h.firstSeen = newDate;
    h.description = inputText;
    $("#root").find("#" + heroid).replaceWith(renderHeroCard(h))
};

function zero(n){
    if(n <= 9){
      return "0" + n;
    }
    return n
}

function tzReduce(n){
    if(n.includes("GMT")){
        let number = n.split("GMT-");
        for(let i = 0; i < number.length; i++){
            if(number[i] != 0){
                return number[i];
            }
        }
    }
}

/**
 * Given an array of hero objects, this function converts the data into HTML,
 *     loads it into the DOM, and adds event handlers.
 * @param  heroes  An array of hero objects to load (see data.js)
 */
export const loadHeroesIntoDOM = function(heroes) {
    // Grab a jQuery reference to the root HTML element
    const $root = $('#root');

    // TODO: Generate the heroes using renderHeroCard()
    //       NOTE: Copy your code from a04 for this part

    heroes.forEach(id => {
        $root.append(renderHeroCard(id))
    });

    // TODO: Append the hero cards to the $root element
    //       NOTE: Copy your code from a04 for this part

    // TODO: Use jQuery to add handleEditButtonPress() as an event handler for
    //       clicking the edit button

    $('#root').on('click', '.edit', handleEditButtonPress);
    // $('.edit').on('click', handleEditButtonPress);

    // TODO: Use jQuery to add handleEditFormSubmit() as an event handler for
    //       submitting the form

    $('#root').on('click', '.submit', handleEditFormSubmit);
    
    // TODO: Use jQuery to add handleCancelButtonPress() as an event handler for
    //       clicking the cancel button
    $('#root').on('click', '.cancel', handleCancelButtonPress);
};



/**
 * Use jQuery to execute the loadHeroesIntoDOM function after the page loads
 */
$(function() {
    loadHeroesIntoDOM(heroicData);
});

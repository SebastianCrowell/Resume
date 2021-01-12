$(document).ready(async function index(){
  const result = await axios({
      method: 'get',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
      withCredentials: true,
    });
  
  var i = 0;
  const data = await result.data;
  $(".content").append('<button onclick="createBuffer()" type="create">Create Tweet</button>')
  data.forEach(element => {
      $("#feed").append(
      '<div class="block" id="' + data[i].id + '">'
      + '<card>' 
      + '<div class="card-title">'
      + data[i].author 
      + '</div>'
      + '<div id="' + data[i].id + '">'
      + data[i].body
      + '</div>'
      + '<div class="card-buttons">'
      + '<button onclick="updateBuffer(' + data[i].id + ')" type="update">Update</button>' 
      + '<button onclick="reset(' + data[i].id + ')" type="read">Read</button>'
      + '<button onclick="remove(' + data[i].id + ')" type="remove">Remove</button>'
      + '<button onclick="like(' + data[i].id + ')" type="like">Like</button>'
      + '<button onclick="unlike(' + data[i].id + ')" type="unlike">Unlike</button>'
      + '<button onclick="retweetBuffer(' + data[i].id + ')" type="retweet">Retweet</button>'
      + '<button onclick="replyBuffer(' + data[i].id + ')" type="reply">Reply</button>'
      + '<div>'
      + 'Likes = '
      + data[i].someLikes.length
      + ', Re-tweets = '
      + data[i].retweetCount
      + '</div>'
      + '</div>'
      + '</card>'
      + '</div>'
      );
      i++;
  });
});

async function createBuffer(){
  const $tform = $('#input-form');
  $tform.empty();
  $tform.append('<textarea id="inputText"></textarea><input id="submit" type="submit"></input>');
  
  $tform.submit(async function (e) { 
      e.preventDefault();

      const data = document.getElementById('inputText').value;

      $('#input-form').empty();
      create(data);
  });
};

async function create(data) {
  const result = await axios({
      method: 'post',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
      withCredentials: true,
      params:{
          body: data
      }
    });
    const make = await result.data;
    $('#feed').prepend(
      '<div class="block" id="' + make.id + '">'
      + '<card class="tweets">' 
      + '<div class="card-title">'
      + make.author 
      + '</div>'
      + '<div id="' + make.id + '">'
      + make.body 
      + '</div>'
      + '<div class="card-buttons">'
      + '<button onclick="updateBuffer(' + make.id + ')" type="update">Update</button>' 
      + '<button onclick="reset(' + make.id + ')" type="read">Read</button>'
      + '<button onclick="remove(' + make.id + ')" type="remove">Remove</button>'
      + '<button onclick="like(' + make.id + ')" type="like">Like</button>'
      + '<button onclick="unlike(' + make.id + ')" type="unlike">Unlike</button>'
      + '<button onclick="retweetBuffer(' + make.id + ')" type="retweet">Retweet</button>'
      + '<button onclick="replyBuffer(' + make.id + ')" type="reply">Reply</button>'
      + '<div>'
      + 'Likes = '
      + make.someLikes.length
      + ', Re-tweets = '
      + make.retweetCount
      + '</div>'
      + '</div>'
      + '</card>'
      + '</div>'
    );
};

var level = 0;

function reset(id){
  $("#read").empty();
  read(id);
  level = 0;
}

async function read(id) {
  const result = await axios({
      method: 'get',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets/' + id + '',
      withCredentials: true,
  });
  const data = await result.data;
  $("#read").append(
      '<div class="card-read">'
      + level
      + '-Level-Tweet: '
      + '<div class="card-title">'
      + 'Author: '
      + data.author 
      + '</div>'
      + 'Tweet: '
      + data.body 
      + '</div>'
  );
  level++;
  if(data.parent != undefined){
  $("#read").append(
      '<div class="card-parent">'
      + level
      + '-Level-Tweet: '
      + '<div class="card-title">'
      + 'Author: '
      + data.parent.author
      + '</div>'
      + 'Tweet: '
      + data.parent.body
      + '</div>'
  );
  level++;
      if(data.parent.parent != undefined){
          read(data.parent.parent.id);
      }
  }
};

async function updateBuffer(id){
  const result = await axios({
      method: 'get',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets/' + id + '',
      withCredentials: true,
  });
  const val = await result.data.body;
  const $tform = $('#input-form');
  $tform.empty();
  $tform.append('<textarea id="inputText">' + val + '</textarea><input id="submit" type="submit"></input>');
  
  $tform.submit(async function (e) { 
      e.preventDefault();

      const data = document.getElementById('inputText').value;
      $('#input-form').empty();
      update(id, data);
  });
};

async function update(id, data) {
  const result = await axios({
      method: 'put',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets/' + id + '',
      withCredentials: true,
      data: {
          body: data
      }
  });
  const target = document.getElementById(id);
  target.innerHTML = data;
};

async function remove(id){
  const result = await axios({
      method: 'delete',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets/' + id + '',
      withCredentials: true,
  });
  const target = document.getElementById(id);
  target.parentNode.removeChild(target);
}

async function like(id){
  const result = await axios({
      method: 'put',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets/' + id + '/like',
      withCredentials: true,
  });
}

async function unlike(id){
  const result = await axios({
      method: 'put',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets/' + id + '/unlike',
      withCredentials: true,
  });
}

async function retweetBuffer(id){
  const $tform = $('#input-form');
  $tform.empty();
  $tform.append('<textarea id="inputText"></textarea><input id="submit" type="submit"></input>');
  
  $tform.submit(async function (e) { 
      e.preventDefault();

      const data = document.getElementById('inputText').value;

      $('#input-form').empty();
      retweet(id, data);
  });
};

async function retweet(id, data){
      const result = await axios({
          method: 'post',
          url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
          withCredentials: true,
          data:{
              "type": "retweet",
              "parent": id,
              "body": data
          }
        });
}

async function replyBuffer(id){
  const $tform = $('#input-form');
  $tform.empty();
  $tform.append('<textarea id="inputText"></textarea><input id="submit" type="submit"></input>');
  
  $tform.submit(async function (e) { 
      e.preventDefault();

      const data = document.getElementById('inputText').value;

      $('#input-form').empty();
      reply(id, data);
  });
};

async function reply(id, data){
  const result = await axios({
      method: 'post',
      url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
      withCredentials: true,
      data:{
          "type": "reply",
          "parent": id,
          "body": data
      }
    });
}
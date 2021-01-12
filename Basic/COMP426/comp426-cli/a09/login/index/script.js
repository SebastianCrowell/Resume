$(function() {
    const $button = $('#tweet-button');
    const $message = $('#message');
  
    $button.submit(function(e) {
      e.preventDefault();
  
      $message.html('');
      
      $.ajax({
        url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
        type: 'GET',
        data,
        xhrFields: {
            withCredentials: true,
        },
      }).then(() => {
        $message.html('<span class="has-text-success">Success! You are now logged in.</span>');
      }).catch(() => {
        $message.html('<span class="has-text-danger">Something went wrong and you were not logged in. Check your email and password and your internet connection.</span>');
      });
    });
  });
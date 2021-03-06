$(document).ready(function () {
  $('#sellerLoginButton').on('click', function () {
    var haveValidationErrors = checkAndDisplayValidationErrors($('#sellerLoginForm').find('input'), '#sellerLoginErrorMessages');

    if (haveValidationErrors) {
      return false;
    }
  });

  // processes validation errors for the given input.  returns true if there
  // are validation errors, false otherwise
  function checkAndDisplayValidationErrors(input, errorMessagesDiv) {
    var divHeight = 800;
    // clear displayed error message if there are any
    clearErrorMessages();
    // check for HTML5 validation errors and process/display appropriately
    // a place to hold error messages
    var errorMessages = [];

    // loop through each input and check for validation errors
    input.each(function () {
      // Use the HTML5 validation API to find the validation errors
      if (!this.validity.valid) {
        var errorField = $('label[for=' + this.id + ']').text();
        errorMessages.push(errorField + ': ' + this.validationMessage);
      }
    });

    // put any error messages in the errorMessages div
    if (errorMessages.length > 0) {
      $.each(errorMessages, function (index, message) {
        $(errorMessagesDiv).append($('<li>').attr({
          class: 'list-group-item list-group-item-danger'
        }).text(message));
        divHeight += 80;
        $('.seller-login-page-container').css('height', divHeight + 'px');
        $('.seller-login-form-container-opacity').css('height', divHeight + 'px');
      });
      // return true, indicating that there were errors
      return true;
    } else {
      // return false, indicating that there were no errors
      return false;
    }
  }

  function clearErrorMessages() {
    $('#sellerLoginErrorMessages').empty();
  }
});
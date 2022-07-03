
    // String firstname = req.getParameter("firstname");
    // String lastname = req.getParameter("lastname");
    // String username = req.getParameter("username");
    // String email = req.getParameter("email");
    // String password = req.getParameter("password");


    const generateUserSettingsForm = () => {
      const form_container = document.getElementById('user-settings-form-container');
      form_container.innerHTML = '';
      const user = JSON.parse(localStorage.getItem('user'));
    
      // create a new  form
      const form = document.createElement('form');
      form.setAttribute('method', 'POST');
      form.setAttribute('action', 'me');
    
      // input element for reimbursement amount
      const nameField = () => {
        // create div
        const form_row = document.createElement('div');
        form_row.setAttribute('class', 'form-row');
    
        const label = document.createElement('label');
        label.setAttribute('for', 'Name');
        label.innerText = 'Name';
        form_row.appendChild(label);
    
        const input_group = document.createElement('div');
        input_group.setAttribute('class', 'input-group flex-nowrap');
    
          const firstNameInput = document.createElement('input');
          const firstNameFieldAttributes = {
            'id': 'firstname',
            'type': 'string',
            'name': 'firstname',
            'value': user.firstName,
          };

        Object.keys(firstNameFieldAttributes).forEach(key => firstNameInput.setAttribute(key, firstNameFieldAttributes[key]));
        input_group.appendChild(firstNameInput);
        form_row.appendChild(input_group);

        const lastNameInput = document.createElement('input');
        const lastNameFieldAttributes = {
          'id': 'lastname',
          'type': 'string',
          'name': 'lastname',
          'value': user.lastName,
        };

      Object.keys(lastNameFieldAttributes).forEach(key => lastNameInput.setAttribute(key, lastNameFieldAttributes[key]));
      input_group.appendChild(lastNameInput);
      form_row.appendChild(input_group);

        
        return form_row;
      }
    
      const username = () => {
        const form_row = document.createElement('div');
        form_row.setAttribute('class', 'form-row');
    
        const label = document.createElement('label');
        label.setAttribute('for', 'username');
        label.innerText = 'Username';
        form_row.appendChild(label);
    
        const input_group = document.createElement('div');
        input_group.setAttribute('class', 'input-group flex-nowrap');
    
          const input = document.createElement('input');
          const inputAttributes = {
            'id': 'username',
            'type': 'string',
            'name': 'username',
            'value': user.username,
          };

        Object.keys(inputAttributes).forEach(key => input.setAttribute(key, inputAttributes[key]));
        input_group.appendChild(input);
        form_row.appendChild(input_group);

        return form_row;
      }

      const email = () => {
        const form_row = document.createElement('div');
        form_row.setAttribute('class', 'form-row');
    
        const label = document.createElement('label');
        label.setAttribute('for', 'email');
        label.innerText = 'Email';
        form_row.appendChild(label);
    
        const input_group = document.createElement('div');
        input_group.setAttribute('class', 'input-group flex-nowrap');
    
          const input = document.createElement('input');
          const inputAttributes = {
            'id': 'email',
            'type': 'email',
            'name': 'email',
            'value': user.email,
          };

        Object.keys(inputAttributes).forEach(key => input.setAttribute(key, inputAttributes[key]));
        input_group.appendChild(input);
        form_row.appendChild(input_group);

        return form_row;
      }

      const currentPassword = () => {
        const form_row = document.createElement('div');
        form_row.setAttribute('class', 'form-row');
    
        const label = document.createElement('label');
        label.setAttribute('for', 'currentPassword');
        label.innerText = 'Current Password';
        form_row.appendChild(label);
    
        const input_group = document.createElement('div');
        input_group.setAttribute('class', 'input-group flex-nowrap');
    
          const input = document.createElement('input');
          const inputAttributes = {
            'id': 'currentPassword',
            'type': 'password',
            'name': 'currentPassword',
            'value': '',
          };

        Object.keys(inputAttributes).forEach(key => input.setAttribute(key, inputAttributes[key]));
        input_group.appendChild(input);
        form_row.appendChild(input_group);

        return form_row;
      }

      const newpassword = () => {
        const form_row = document.createElement('div');
        form_row.setAttribute('class', 'form-row');
    
        const label = document.createElement('label');
        label.setAttribute('for', 'password');
        label.innerText = 'New Password';
        form_row.appendChild(label);
    
        const input_group = document.createElement('div');
        input_group.setAttribute('class', 'input-group flex-nowrap');
    
          const input = document.createElement('input');
          const inputAttributes = {
            'id': 'newPassword',
            'type': 'password',
            'name': 'newPassword',
            'value': '',
          };

        Object.keys(inputAttributes).forEach(key => input.setAttribute(key, inputAttributes[key]));
        input_group.appendChild(input);
        form_row.appendChild(input_group);

        return form_row;
      }
        
    
    
      const submit = () => {
        const form_row = document.createElement('div');
        form_row.setAttribute('class', 'form-row');
        const submit = document.createElement('button');
        submit.setAttribute('type', 'submit');
        submit.setAttribute('class', 'btn btn-primary');
        submit.setAttribute('id', 'submit');
        submit.innerText = 'Save';
        form_row.appendChild(submit);
        return form_row;
      }
    
      form.appendChild(nameField());
      form.appendChild(username());
      form.appendChild(email());
      form.appendChild(currentPassword());
      form.appendChild(newpassword());
      form.appendChild(submit());
      form_container.appendChild(form);
    }
    


userSettingsModalCloseHandler = () => {
  document.getElementById('user-settings-modal').setAttribute('class', 'modal');
  document.getElementById('modal-backdrop').setAttribute('class', '');
  document.getElementById('user-settings-form-container').innerHTML = '';
}

document.getElementById('user-settings-button').addEventListener('click', () => {
    document.getElementById('user-settings-modal').setAttribute('class', 'modal active');
    document.getElementById('modal-backdrop').setAttribute('class', 'active');
    document.getElementById('modal-backdrop').addEventListener('click', userSettingsModalCloseHandler);
    document.getElementById('user-settings-modal-close').addEventListener('click', userSettingsModalCloseHandler);
    generateUserSettingsForm();
});
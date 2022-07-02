const form_container = document.getElementById('reimbursement-form');

// create a new reimbursement form
const form = document.createElement('form');
form.setAttribute('method', 'POST');
form.setAttribute('action', 'new-reimbursement');

// input element for reimbursement amount
const amount = () => {
  // create div
  const form_row = document.createElement('div');
  form_row.setAttribute('class', 'form-row');

  const label = document.createElement('label');
  label.setAttribute('for', 'amount');
  label.innerText = 'Amount';
  form_row.appendChild(label);

  const input_group = document.createElement('div');
  input_group.setAttribute('class', 'input-group');
    const input_group_prepend = document.createElement('div');
    input_group_prepend.setAttribute('class', 'input-group-prepend');
      const input_group_text = document.createElement('div');
      input_group_text.setAttribute('class', 'input-group-text');
      input_group_text.innerText = '$';
      input_group_prepend.appendChild(input_group_text);
    input_group.appendChild(input_group_prepend);

    const input = document.createElement('input');
    const input_attributes = {
      'type': 'number',
      'name': 'amount',
      'value': 0,
      'min': 0,
      'max': 9999999,
      'step': 0.01,
      'data-number-to-currency-locale': 'en-US',
      'data-number-to-currency-symbol': '$',
      'data-mumber-stepfactor': '100',
      'class': 'form-control currency',
      'required': true
    }
  Object.keys(input_attributes).forEach(key => input.setAttribute(key, input_attributes[key]));
  input_group.appendChild(input);
  form_row.appendChild(input_group);
  return form_row;
}

const type = () => {
  const form_row = document.createElement('div');
  form_row.setAttribute('class', 'form-row');

  const label = document.createElement('label');
  label.setAttribute('for', 'type');
  label.innerText = 'Type';
  form_row.appendChild(label);

  const input_attributes = {
    'class': 'form-control',
    'name' : 'type',
    'required': true
  };

  const createOption = (value, text) => {
    const option = document.createElement('option');
    option.setAttribute('value', value);
    option.innerText = text;
    return option;
  }

  const input = document.createElement('select');
  Object.keys(input_attributes).forEach(key => input.setAttribute(key, input_attributes[key]));
  input.appendChild(createOption('Other', 'Other'));
  input.appendChild(createOption('Lodging', 'Lodging'));
  input.appendChild(createOption('Travel', 'Travel'));
  input.appendChild(createOption('Food', 'Food'));

  form_row.appendChild(input);
  return form_row;
}

const description = () => {
  const form_row = document.createElement('div');
  form_row.setAttribute('class', 'form-row');

  const label = document.createElement('label');
  label.setAttribute('for', 'type');
  label.innerText = 'Type';
  form_row.appendChild(label);

  const input_attibutes = {
    'class': 'form-control',
    'name' : 'description',
    'maxlength': '255',
    'required': true
  };
  const input = document.createElement('textarea');
  Object.keys(input_attibutes).forEach(key => input.setAttribute(key, input_attibutes[key]));
  form_row.appendChild(input);
  return form_row;
}

const submit = () => {
  const form_row = document.createElement('div');
  form_row.setAttribute('class', 'form-row');
  const submit = document.createElement('button');
  submit.setAttribute('type', 'submit');
  submit.setAttribute('class', 'btn btn-sucess');
  submit.setAttribute('id', 'submit');
  submit.innerText = 'Submit';
  form_row.appendChild(submit);
  return form_row;
}


form.appendChild(amount());
form.appendChild(type());
form.appendChild(description());
form.appendChild(submit());
form_container.appendChild(form);
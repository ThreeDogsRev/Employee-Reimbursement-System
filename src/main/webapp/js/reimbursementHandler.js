const newReimbursementModalOpenHandler = () => {
  document
    .getElementById("new-reimbursement-modal")
    .setAttribute("class", "modal active");
  document.getElementById("modal-backdrop").setAttribute("class", "active");
  document
    .getElementById("modal-backdrop")
    .addEventListener("click", newReimbursementModalCloseHandler);
  generateForm();
};

const newReimbursementModalCloseHandler = () => {
  document
    .getElementById("new-reimbursement-modal")
    .setAttribute("class", "modal");
  document.getElementById("modal-backdrop").setAttribute("class", "");
  document.getElementById("reimbursement-form").innerHTML = "";
};

const generateForm = () => {
  const form_container = document.getElementById("reimbursement-form");

  // create a new reimbursement form
  const form = document.createElement("form");
  form.setAttribute("method", "POST");
  form.setAttribute("action", "new-reimbursement");

  // input element for reimbursement amount
  const amount = () => {
    // create div
    const form_row = document.createElement("div");
    form_row.setAttribute("class", "form-row");

    const label = document.createElement("label");
    label.setAttribute("for", "amount");
    label.innerText = "Amount";
    form_row.appendChild(label);

    const input_group = document.createElement("div");
    input_group.setAttribute("class", "input-group");
    const input_group_prepend = document.createElement("div");
    input_group_prepend.setAttribute("class", "input-group-prepend");
    const input_group_text = document.createElement("div");
    input_group_text.setAttribute("class", "input-group-text");
    input_group_text.innerText = "$";
    input_group_prepend.appendChild(input_group_text);
    input_group.appendChild(input_group_prepend);

    const input = document.createElement("input");
    const input_attributes = {
      id: "reimbursement-form-amount",
      type: "number",
      name: "amount",
      value: 0,
      min: 0,
      max: 9999999,
      step: 0.01,
      "data-number-to-currency-locale": "en-US",
      "data-number-to-currency-symbol": "$",
      "data-mumber-stepfactor": "100",
      class: "form-control currency",
      required: true,
    };
    Object.keys(input_attributes).forEach((key) =>
      input.setAttribute(key, input_attributes[key])
    );
    input_group.appendChild(input);
    form_row.appendChild(input_group);
    return form_row;
  };

  const type = () => {
    const form_row = document.createElement("div");
    form_row.setAttribute("class", "form-row");

    const label = document.createElement("label");
    label.setAttribute("for", "type");
    label.innerText = "Type";
    form_row.appendChild(label);

    const input_attributes = {
      id: "reimbursement-form-type",
      class: "form-control",
      name: "type",
      required: true,
    };

    const createOption = (value, text) => {
      const option = document.createElement("option");
      option.setAttribute("value", value);
      option.innerText = text;
      return option;
    };

    const input = document.createElement("select");
    Object.keys(input_attributes).forEach((key) =>
      input.setAttribute(key, input_attributes[key])
    );
    input.appendChild(createOption("Other", "Other"));
    input.appendChild(createOption("Lodging", "Lodging"));
    input.appendChild(createOption("Travel", "Travel"));
    input.appendChild(createOption("Food", "Food"));

    form_row.appendChild(input);
    return form_row;
  };

  const description = () => {
    const form_row = document.createElement("div");
    form_row.setAttribute("class", "form-row");

    const label = document.createElement("label");
    label.setAttribute("for", "type");
    label.innerText = "Type";
    form_row.appendChild(label);

    const input_attibutes = {
      id: "reimbursement-form-description",
      class: "form-control",
      name: "description",
      maxlength: "255",
      required: true,
    };
    const input = document.createElement("textarea");
    Object.keys(input_attibutes).forEach((key) =>
      input.setAttribute(key, input_attibutes[key])
    );
    form_row.appendChild(input);
    return form_row;
  };

  const submit = () => {
    const form_row = document.createElement("div");
    form_row.setAttribute("class", "form-row");
    const submit = document.createElement("button");
    submit.setAttribute("type", "submit");
    submit.setAttribute("class", "btn btn-primary");
    submit.setAttribute("id", "submit");
    submit.innerText = "Submit";
    form_row.appendChild(submit);
    return form_row;
  };

  form.appendChild(amount());
  form.appendChild(type());
  form.appendChild(description());
  form.appendChild(submit());
  form_container.appendChild(form);
};

const fetchUsersReimbursements = () => {
  return fetch("me")
    .then((response) => response.json())
    .then((data) => {
      return data.reimbursements;
    });
};

const drawReimbursements = (reimbursements) => {
  const table = document.createElement("table");
  table.setAttribute("id", "reimbursements-table");
  table.setAttribute("class", "table table-striped table-borderless");

  if (!reimbursements) {
    const no_reimbursements = document.createElement("div");
    no_reimbursements.setAttribute("class", "no-reimbursements");
    no_reimbursements.innerText = "No reimbursements found create one!";
    document.getElementById("reimbursements-card").appendChild(no_reimbursements);
    return;
  }

  const header = document.createElement("thead");
  header.appendChild(document.createElement("th")).innerText = "Id";
  header.appendChild(document.createElement("th")).innerText = "Status";
  header.appendChild(document.createElement("th")).innerText = "Type";
  header.appendChild(document.createElement("th")).innerText = "Amount";
  header.appendChild(document.createElement("th")).innerText = "Description";

  table.appendChild(header);
  const body = table.appendChild(document.createElement("tbody"));

  reimbursements.forEach((reimbursement) => {
    const row = document.createElement("tr");
    const id = document.createElement("td");
    id.innerText = reimbursement.id;
    const status = document.createElement("td");
    status.innerText = reimbursement.status;
    const type = document.createElement("td");
    type.innerText = reimbursement.type;
    const amount = document.createElement("td");
    amount.innerText = `$${reimbursement.amount / 100}`;
    const description = document.createElement("td");
    description.innerText = reimbursement.description;

    row.appendChild(id);
    row.appendChild(status);
    row.appendChild(type);
    row.appendChild(amount);
    row.appendChild(description);
    body.appendChild(row);
  });
  document.getElementById("reimbursements-card").innerHTML = "";
  document.getElementById("reimbursements-card").appendChild(table);
};

const drawLoading = () => {
  const spinner_border = document.createElement("div");
  spinner_border.setAttribute("class", "spinner-border");
  const spinner_border_span = document.createElement("span");
  spinner_border_span.setAttribute("class", "sr-only");
  spinner_border.appendChild(spinner_border_span);
  document.getElementById("reimbursements-card").appendChild(spinner_border);
}


// drawLoading();

document
  .getElementById("new-reimbursement-modal-close")
  .addEventListener("click", newReimbursementModalCloseHandler);

document
  .getElementById("new-reimbursement-modal-open")
  .addEventListener("click", newReimbursementModalOpenHandler);

fetchUsersReimbursements().then((reimbursements) => {
  drawReimbursements(reimbursements);
});

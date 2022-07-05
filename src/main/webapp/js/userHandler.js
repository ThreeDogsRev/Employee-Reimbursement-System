document.getElementById("user-settings-button").addEventListener("click", () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const modalRoot = document.getElementById("user-settings-modal");

  modalRoot.querySelector('input[name="firstname"]').value = user.firstName;
  modalRoot.querySelector('input[name="lastname"]').value = user.lastName;
  modalRoot.querySelector('input[name="username"]').value = user.username;
  modalRoot.querySelector('input[name="email"]').value = user.email;

  const bsModal = new bootstrap.Modal(modalRoot);
  bsModal.toggle();
});

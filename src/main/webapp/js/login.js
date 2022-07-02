

const check_logged_in = () => {
    return fetch('/employee-reimbursement-system/me')
        .then(response => response.json())
        .then(user => {
            if(user == null){
                document.getElementById('login-form').setAttribute('class', 'active')
                document.getElementById('dashboard').setAttribute('class', '')
                document.getElementById('loading').setAttribute('class', '')
            } else{
                document.getElementById('login-form').setAttribute('class', '')
                document.getElementById('dashboard').setAttribute('class', 'active')
                document.getElementById('loading').setAttribute('class', '')
            }
        });
}

check_logged_in();

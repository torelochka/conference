function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}

function set_cookie(name, value) {
    document.cookie = name + '=' + value + '; Path=/;';
}

function delete_cookie(name) {
    document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

window.Event = new Vue({
    data: {isLoggedIn: false}
});

Vue.component('login-component', {
    template: '<div class="navbar-nav" v-if="!isLoggedIn()">' +
        '    <a class="nav-item nav-link active" href="/">Main</a>' +
        '    <a class="nav-item nav-link active" href="/login">Login</a>' +
        '    <a class="nav-item nav-link active" href="/registration">Register</a>' +
        '</div>' +
        '<div class="navbar-nav" v-else>' +
        '    <a class="nav-item nav-link active" href="/">Main</a>' +
        '    <a class="nav-item nav-link active" href="/talk/create" v-if="isSpeaker()">Create talk</a>' +
        '    <a class="nav-item nav-link active" href="/admin" v-if="isAdmin()">Admin page</a>' +
        '    <a class="nav-item nav-link active" v-on:click="logOut">Logout</a>' +
        '</div>',
    data: function () {
        return {
            profileHref: "",
            user: {},
        }
    },
    mounted() {
        if (getCookie("access_token")) {
            axios.get("/getUser/?access_token=" + getCookie("access_token"))
                .then(function (response) {
                    console.log(response)
                    this.user = response.data
                    this.profileHref = "/profile/" + response.data.id;
                    window.Event.isLoggedIn = true;
                    Event.$emit('logged-in');
                }.bind(this))
                .catch(function (error) {
                    console.log("error ! \n" + error.response)
                    delete_cookie("access_token");
                    return error;
                });
        }
    },
    methods: {
        logOut() {
            window.Event.isLoggedIn = false;
            this.logged_in_msg = "Successfully logged out";
            delete_cookie("access_token")
        },
        isLoggedIn() {
            return window.Event.isLoggedIn;
        },
        isSpeaker() {
            return this.user.role === "SPEAKER"
        },
        isAdmin() {
            return this.user.role === "ADMIN"
        }
    }
});